package com.smart.resources.schools_app.features.onlineExam.data.remote.dataSource

import com.hadiyarajesh.flower.ApiResponse
import com.smart.resources.schools_app.features.onlineExam.data.local.model.LocalAnswer
import com.smart.resources.schools_app.features.onlineExam.data.remote.model.NetworkAnswer
import com.smart.resources.schools_app.features.onlineExam.data.remote.model.NetworkQuestion
import kotlinx.coroutines.flow.Flow
import retrofit2.http.*

interface AnswersClient {
    @GET("examQuestions/{examId}")
    fun getExamAnswers(@Path("examId") examId:String): Flow<ApiResponse<List<NetworkQuestion>>>

    @POST("addMultiAnswer")
    fun addAnswers(@Body answers: List<NetworkAnswer>): Flow<ApiResponse<List<NetworkAnswer>>>
}