package com.smart.resources.schools_app.features.login

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.util.*
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection


class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val context = getApplication<Application>().applicationContext
    var onLogin: (()->Unit)?= null
    var onLoginError: ((errorMsg:String)->Unit)?= null


    val loginException = LoginException()
    val isTeacherLogging = MutableLiveData<Boolean>()
    val isStudentLogging = MutableLiveData<Boolean>()

    var phoneNumber: String? = "770000000"
    var password: String? = "pass2"


    fun login(view: View) {
        if (!isDataValid()) return

        if (view.id == R.id.loginAsTeacherBtn)
            isTeacherLogging.postValue(true)
        else isStudentLogging.postValue(true)

        viewModelScope.launch {
            val phone = phoneNumber.toString().withEngNums()
            val pass = password.toString().withEngNums()

            when (val token = LoginRepository.loginStudent(phone, pass)) {
                is Success -> {
                    setupSharedPrefs(view, token)
                    withContext(Main) { onLogin?.invoke() }
                }
                is ResponseError -> getErrorMsg(token)
                is ConnectionError -> onLoginError?.invoke(context.getString(R.string.connection_error))

            }

            isTeacherLogging.postValue(false)
            isStudentLogging.postValue(false)
        }
    }

    private fun setupSharedPrefs(
        view: View,
        result: Success<String>
    ) {
        SharedPrefHelper.instance?.apply {
            userType = if (view.id == R.id.loginAsTeacherBtn) UserType.TEACHER else UserType.STUDENT
            accessToken = result.data
        }
    }

    private fun getErrorMsg(result: ResponseError) {
        if (result.statusCode == HttpURLConnection.HTTP_BAD_REQUEST)
            onLoginError?.invoke(context.getString(R.string.wrong_login_info))
        else
            onLoginError?.invoke(result.combinedMsg)
    }

    private fun isDataValid(): Boolean {
        if (phoneNumber.isNullOrEmpty()) {
            loginException.phoneNumberMsg.postValue(context.getString(R.string.field_required))
            return false
        }

        if (password.isNullOrEmpty()) {
            loginException.passwordMsg.postValue(context.getString(R.string.field_required))
            return false
        }

        return true
    }


}

