package com.smart.resources.schools_app.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.smart.resources.schools_app.database.dao.AccountDao
import com.smart.resources.schools_app.util.BackendHelper
import com.smart.resources.schools_app.util.LoginException
import com.smart.resources.schools_app.viewModel.myInterface.LoginViewListener
import retrofit2.create


class LoginViewModel : ViewModel() {
    var listener:LoginViewListener?= null

    val loginException = MutableLiveData<LoginException>()

    val isTeacherLogging= MutableLiveData<Boolean>()
    val isStudentLogging= MutableLiveData<Boolean>()


    var phoneNumber: String?= null
    var password: String?= null


    fun loginAsTeacher(){
//        BackendHelper.retrofit.create(AccountDao::class.java)
//            .
         listener?.loginAsTeacher()
    }
}

