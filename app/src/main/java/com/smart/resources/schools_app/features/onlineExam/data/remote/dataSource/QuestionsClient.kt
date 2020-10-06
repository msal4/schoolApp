package com.smart.resources.schools_app.features.onlineExam.data.remote.dataSource

import com.hadiyarajesh.flower.ApiResponse
import com.smart.resources.schools_app.features.onlineExam.data.remote.model.NetworkQuestion
import kotlinx.coroutines.flow.Flow
import retrofit2.http.*

interface QuestionsClient {
    @POST("addQuestions")
    fun addQuestion(@Body networkQuestion: NetworkQuestion): Flow<ApiResponse<List<NetworkQuestion>>>

    @POST("addMultiQuestion")
    fun addQuestions(@Body networkQuestion: List<NetworkQuestion>): Flow<ApiResponse<List<NetworkQuestion>>>
}