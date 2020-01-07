package com.smart.resources.schools_app.features.homework

import retrofit2.Response
import retrofit2.http.GET

interface HomeworkDao {
    @GET("classHomework")
    suspend fun fetchHomework(): Response<List<HomeworkModel>>
}