package com.smart.resources.schools_app.features.onlineExam.data.remote.dataSource

import com.hadiyarajesh.flower.ApiResponse
import com.smart.resources.schools_app.features.onlineExam.data.remote.model.NetworkAnswer
import com.smart.resources.schools_app.features.onlineExam.data.remote.model.PostNetworkAnswer
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AnswersClient {
    @GET("studentAnswer")
    fun getStudentExamAnswers(
        @Query("onlineExamId") examId: String,
        @Query("studentId") studentId: String,
    ): Flow<ApiResponse<List<NetworkAnswer>>>

    @POST("addMultiAnswer")
    fun addAnswers(@Body answers: List<PostNetworkAnswer>): Flow<ApiResponse<List<NetworkAnswer>>>
}