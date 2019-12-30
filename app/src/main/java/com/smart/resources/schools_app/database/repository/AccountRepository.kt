package com.smart.resources.schools_app.database.repository

import com.orhanobut.logger.Logger
import com.smart.resources.schools_app.database.dao.AccountDao
import com.smart.resources.schools_app.util.*


object AccountRepository{
    private val accountDao=  BackendHelper.retrofit.create(AccountDao::class.java)

    suspend fun login(phoneNumber:String, password:String): MyResult<Unit>
            = MyResult.fromResponse(accountDao.login(phoneNumber, password))
}



