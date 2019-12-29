package com.smart.resources.schools_app.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.smart.resources.schools_app.util.LoginException
import com.smart.resources.schools_app.viewModel.myInterface.LoginViewListener


class LoginViewModel : ViewModel() {
    var listener:LoginViewListener?= null

    val loginException = MutableLiveData<LoginException>()

    val isTeacherLogging= MutableLiveData<Boolean>()
    val isStudentLogging= MutableLiveData<Boolean>()


    var phoneNumber: String?= null
    var password: String?= null


    fun loginAsTeacher(){
         listener?.loginAsTeacher()
    }
}

