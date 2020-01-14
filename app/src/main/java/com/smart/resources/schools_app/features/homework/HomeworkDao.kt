package com.smart.resources.schools_app.features.homework

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface HomeworkDao {
    @GET("classHomework")
    suspend fun fetchHomework(): Response<List<HomeworkModel>>

    @Multipart
    @POST("addHomework")
    suspend fun addHomework(
        @Part("subjectName") subjectName: RequestBody,
        @Part("assignmentName") assignmentName: RequestBody,
        @Part("date") date: RequestBody,
        @Part("note") note: RequestBody,
        @Part attachment: MultipartBody.Part?,
        @Part("classId") classId: RequestBody
    ): Response<HomeworkModel>

}