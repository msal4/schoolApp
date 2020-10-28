package com.smart.resources.schools_app.features.homework

import com.smart.resources.schools_app.core.myTypes.MyResult
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface HomeworkClient {
    @GET("classHomeworkStudent")
    suspend fun fetchHomeworkWithSolution(): MyResult<List<HomeworkWithSolution>>

    @GET("classHomework")
    suspend fun fetchHomework(): MyResult<List<HomeworkModel>>

    @DELETE("homework/{id}")
    suspend fun deleteHomework(@Path("id") homeworkId: String?): MyResult<Unit>

    @Multipart
    @POST("addHomework")
    suspend fun addHomework(
        @Part("subjectName") subjectName: RequestBody,
        @Part("assignmentName") assignmentName: RequestBody,
        @Part("date") date: RequestBody,
        @Part("note") note: RequestBody,
        @Part attachment: MultipartBody.Part?,
        @Part("classId") classId: RequestBody
    ): MyResult<HomeworkModel>

}