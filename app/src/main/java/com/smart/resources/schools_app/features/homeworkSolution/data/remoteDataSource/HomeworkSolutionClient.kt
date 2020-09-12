package com.smart.resources.schools_app.features.homeworkSolution.data.remoteDataSource

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface HomeworkSolutionClient {

    @Multipart
    @POST("addSolution")
    suspend fun addSolution(
        @Part("homeworkId") homeworkId: RequestBody,
        @Part("studentId") studentId: RequestBody,
        @Part solutionImage: MultipartBody.Part?,
        @Part("solutionText") solutionText: RequestBody
    ): Response<Unit>

}