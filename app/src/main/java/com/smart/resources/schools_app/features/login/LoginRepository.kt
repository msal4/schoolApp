package com.smart.resources.schools_app.features.login

import com.smart.resources.schools_app.core.util.*
import java.lang.Exception


object LoginRepository{
    private val accountDao=  BackendHelper.retrofit.create(LoginDao::class.java)

    suspend fun loginStudent(phoneNumber:String, password:String): MyResult<String>{
        val requestBody= hashMapOf("phone" to phoneNumber, "password" to password)
        return try{

            val myRes= accountDao.loginStudent(requestBody).toMyResult()
            if(myRes is Success) Success(myRes.data?.get("token")?.asString) else myRes as MyResult<Nothing>
        }catch(e:Exception){
            ConnectionError(e)
        }
    }

}



