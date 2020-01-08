package com.smart.resources.schools_app.features.exam

import retrofit2.Response
import retrofit2.http.GET

interface ExamDao {
    @GET("examResult")
    suspend fun fetchExams(): Response<List<ExamModel>>
}