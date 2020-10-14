package com.smart.resources.schools_app.features.onlineExam.data.remote.dataSource

import com.hadiyarajesh.flower.ApiResponse
import com.smart.resources.schools_app.features.onlineExam.data.local.model.LocalAnswer
import com.smart.resources.schools_app.features.onlineExam.data.remote.model.NetworkAnswer
import com.smart.resources.schools_app.features.onlineExam.data.remote.model.NetworkQuestion
import com.smart.resources.schools_app.features.onlineExam.data.remote.model.PostNetworkAnswer
import kotlinx.coroutines.flow.Flow
import retrofit2.http.*

interface AnswersClient {
    @GET("examQuestions/{examId}")
    fun getExamAnswers(examId:String, studentId:String): Flow<ApiResponse<List<NetworkAnswer>>>

    @POST("addMultiAnswer")
    fun addAnswers(@Body answers: List<PostNetworkAnswer>): Flow<ApiResponse<List<NetworkAnswer>>>
}