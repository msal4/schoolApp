package com.smart.resources.schools_app.database.dao

import com.smart.resources.schools_app.database.model.HomeworkModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface HomeworkDao {
    @GET("homeworks")
    suspend fun fetchHomework(): Response<List<HomeworkModel>>
}