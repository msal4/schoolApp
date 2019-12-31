package com.smart.resources.schools_app.database.repository

import com.smart.resources.schools_app.database.dao.AccountDao
import com.smart.resources.schools_app.util.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async


object AccountRepository{
    private val accountDao=  BackendHelper.retrofit.create(AccountDao::class.java)

    suspend fun login(phoneNumber:String, password:String): MyResult<Unit>
            = MyResult.fromResponse(GlobalScope.async{accountDao.login(phoneNumber, password)})
}



