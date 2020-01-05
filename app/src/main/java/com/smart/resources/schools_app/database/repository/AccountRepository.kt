package com.smart.resources.schools_app.database.repository

import com.google.gson.JsonObject
import com.smart.resources.schools_app.database.dao.AccountDao
import com.smart.resources.schools_app.util.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async


object AccountRepository{
    private val accountDao=  BackendHelper.retrofit.create(AccountDao::class.java)

    suspend fun loginStudent(phoneNumber:String, password:String): MyResult<JsonObject>
            = MyResult.fromResponse(GlobalScope.async{accountDao.loginStudent(hashMapOf("phone" to phoneNumber, "password" to password))})
}



