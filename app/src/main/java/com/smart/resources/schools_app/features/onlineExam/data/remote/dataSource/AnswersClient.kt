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
    companion object{
        private const val ONLINE_EXAM_ID_QUERY= "onlineExamId"
        private const val STUDENT_ID_QUERY= "studentId"
    }

    @GET("studentAnswer")
    fun getStudentExamAnswers(
        @Query(ONLINE_EXAM_ID_QUERY) examId: String,
        @Query(STUDENT_ID_QUERY) studentId: String,
    ): Flow<ApiResponse<List<NetworkAnswer>>>

    @POST("addMultiAnswer")
    fun addAnswers(@Body answers: List<PostNetworkAnswer>): Flow<ApiResponse<List<NetworkAnswer>>>
}