package com.smart.resources.schools_app.features.onlineExam.data.remote.dataSource

import com.smart.resources.schools_app.features.onlineExam.data.remote.model.NetworkExamFinishedStatus
import com.smart.resources.schools_app.features.onlineExam.data.remote.model.NetworkOnlineExam
import com.smart.resources.schools_app.features.onlineExam.data.remote.model.NetworkOnlineExamStatus
import com.smart.resources.schools_app.features.onlineExam.data.remote.model.NetworkQuestion
import retrofit2.Response
import retrofit2.http.*

interface QuestionsClient {
    @POST("addQuestions")
    suspend fun addQuestions(@Body networkQuestion: NetworkQuestion): Response<Unit>
}