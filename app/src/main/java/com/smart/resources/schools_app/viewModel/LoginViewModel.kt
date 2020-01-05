package com.smart.resources.schools_app.viewModel

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.database.repository.AccountRepository
import com.smart.resources.schools_app.util.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection


class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val context = getApplication<Application>().applicationContext
    var listener: LoginViewListener?= null

    val loginException = LoginException()

    val isTeacherLogging= MutableLiveData<Boolean>()
    val isStudentLogging= MutableLiveData<Boolean>()

    var phoneNumber: String?= null
    var password: String?= null


            fun login(view: View){
                if (!isDataValid()) return

                if(view.id== R.id.loginAsTeacherBtn) isTeacherLogging.postValue(true)
                else isStudentLogging.postValue(true)

                CoroutineScope(IO).launch {
                    val phone= phoneNumber.toString().withEngNums()
                    val pass=password.toString().withEngNums()

            when(val result= AccountRepository.loginStudent(phone, pass)){
                    is Success -> {
                        SharedPrefHelper.getInstance()?.accessToken= result.data?.get("token")?.asString
                        withContext(Main){listener?.login()}
                    }
                    is ResponseError -> getErrorMsg(result)
                    is ConnectionError->  listener?.loginError(context.getString(R.string.connection_error))

                }

            isTeacherLogging.postValue(false)
            isStudentLogging.postValue(false)
        }
    }

    private fun getErrorMsg(result: ResponseError) {
        if (result.statusCode == HttpURLConnection.HTTP_BAD_REQUEST)
            listener?.loginError(context.getString(R.string.wrong_login_info))
        else
            listener?.loginError(result.combinedMsg)
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

