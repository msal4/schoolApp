package com.smart.resources.schools_app.features.login

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.extentions.withEngNums
import com.smart.resources.schools_app.core.myTypes.*
import com.smart.resources.schools_app.features.profile.StudentModel
import com.smart.resources.schools_app.features.profile.TeacherModel
import com.smart.resources.schools_app.features.users.data.UserAccount
import com.smart.resources.schools_app.features.users.data.UserRepository
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val context = getApplication<Application>().applicationContext
    var onLogin: (() -> Unit)? = null
    var onLoginError: ((errorMsg: String) -> Unit)? = null

    val loginException = LoginException()
    val isTeacherLogging = MutableLiveData<Boolean>()
    val isStudentLogging = MutableLiveData<Boolean>()


    var phoneNumber: String? = null
    var password: String? = null

    var isStudent: Boolean = false

    fun login(view: View) {
        if (!isDataValid()) return

        val userType =
            if (view.id == R.id.loginAsTeacherBtn) {
                isTeacherLogging.postValue(true)
                isStudent = false
                UserType.TEACHER

            } else {
                isStudentLogging.postValue(true)
                isStudent = true
                UserType.STUDENT

            }

        viewModelScope.launch {
            val phone = phoneNumber.toString().withEngNums()
            val pass = password.toString().withEngNums()

            when (val token = getToken(userType, phone, pass)) {
                is Success -> {
                    setupCurrentUser(userType, token)
                    withContext(Main) { onLogin?.invoke() }
                }
                is ResponseError -> onLoginError?.invoke(context.getString(R.string.wrong_login_info))
                is ConnectionError -> onLoginError?.invoke(context.getString(R.string.connection_error))
            }

            isTeacherLogging.postValue(false)
            isStudentLogging.postValue(false)
        }
    }

    private suspend fun getToken(userType: UserType, phone: String, pass: String) =
        with(LoginRepository) {
            if (userType== UserType.STUDENT) loginStudent(phone, pass)
            else loginTeacher(phone, pass)
        }

    private fun setupCurrentUser(
        userType: UserType,
        result: Success<String>
    ) {
        val isTeacher = userType == UserType.TEACHER


        result.data?.let {
            var id= ""

            val person= if (isTeacher) {
                TeacherModel.fromToken(it)?.apply {
                    id= "t${this.id}"
                }
            }
            else {
                StudentModel.fromToken(it)?.apply {
                    id= "s${this.id}"
                }
            }
            person?.apply {
                    UserRepository.instance.insertCurrentUser(
                        UserAccount(
                            id,
                            result.data,
                            "",
                            name,
                            if (isTeacher) 1 else 0
                        )
                    )
            }
        }
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