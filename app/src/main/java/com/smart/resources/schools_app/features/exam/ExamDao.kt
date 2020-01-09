package com.smart.resources.schools_app.features.exam

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ExamDao {
    @GET("examResult")
    suspend fun fetchExams(): Response<List<ExamModel>>


    @POST("addExam")
    suspend fun addExam(@Body examPostModel: ExamPostModel): Response<Unit>
}