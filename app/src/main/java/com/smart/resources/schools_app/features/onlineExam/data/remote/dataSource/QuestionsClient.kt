package com.smart.resources.schools_app.features.onlineExam.data.remote.dataSource

import com.hadiyarajesh.flower.ApiResponse
import com.smart.resources.schools_app.features.onlineExam.data.remote.model.NetworkExamFinishedStatus
import com.smart.resources.schools_app.features.onlineExam.data.remote.model.NetworkOnlineExam
import com.smart.resources.schools_app.features.onlineExam.data.remote.model.NetworkOnlineExamStatus
import com.smart.resources.schools_app.features.onlineExam.data.remote.model.NetworkQuestion
import com.smart.resources.schools_app.features.onlineExam.domain.model.Question
import retrofit2.Response
import retrofit2.http.*

interface QuestionsClient {
    @POST("addQuestions")
    suspend fun addQuestion(@Body networkQuestion: NetworkQuestion): ApiResponse<List<NetworkQuestion>>

    @POST("addMultiQuestion")
    suspend fun addQuestions(@Body networkQuestion: List<NetworkQuestion>): ApiResponse<List<NetworkQuestion>>
}