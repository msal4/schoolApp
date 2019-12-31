package com.smart.resources.schools_app.viewModel

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.orhanobut.logger.Logger
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.database.repository.AccountRepository
import com.smart.resources.schools_app.util.*
import com.smart.resources.schools_app.viewModel.myInterface.LoginViewListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch


class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val context = getApplication<Application>().applicationContext
    var listener:LoginViewListener?= null

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
            when(val result= AccountRepository.login(phoneNumber.toString(), password.toString())){
                    is Success -> listener?.login()
                    is ResponseError -> Logger.e("error code: ${result.statusCode} - msg: ${result.errorBody}")
                    is ConnectionError->  Logger.e("exception : ${result.message}")
                }

            isTeacherLogging.postValue(false)
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

