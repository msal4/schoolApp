package com.smart.resources.schools_app.features.exam

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface ExamDao {
    @GET("examResult")
    suspend fun fetchExams(): Response<List<ExamModel>>


    @POST("addExam")
    suspend fun addExam(@Body postExamModel: PostExamModel): Response<Unit>
}