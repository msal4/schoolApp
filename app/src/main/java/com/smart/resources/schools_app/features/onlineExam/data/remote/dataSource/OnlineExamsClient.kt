package com.smart.resources.schools_app.features.onlineExam.data.remote.dataSource

import com.hadiyarajesh.flower.ApiResponse
import com.smart.resources.schools_app.features.onlineExam.data.remote.model.NetworkExamFinishedStatus
import com.smart.resources.schools_app.features.onlineExam.data.remote.model.onlineExam.NetworkOnlineExam
import com.smart.resources.schools_app.features.onlineExam.data.remote.model.NetworkOnlineExamStatus
import com.smart.resources.schools_app.features.onlineExam.data.remote.model.onlineExam.PostNetworkOnlineExam
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.*

interface OnlineExamsClient {
    companion object{
        const val EXAM_ID_PATH= "id"
    }

    @GET("onlineExams")
    fun getOnlineExams(): Flow<ApiResponse<List<NetworkOnlineExam>>>

    @POST("addOnlineExam")
    fun addOnlineExam(@Body postModel: PostNetworkOnlineExam): Flow<ApiResponse<NetworkOnlineExam>>

    @PUT("examFinish/{$EXAM_ID_PATH}")
    suspend fun finishExam(@Path(EXAM_ID_PATH) examId:String, @Body networkExamFinishedStatus: NetworkExamFinishedStatus): Response<Unit>

    @PUT("updateStatus/{$EXAM_ID_PATH}")
    suspend fun updateStatus(@Path(EXAM_ID_PATH) examId:String, @Body networkOnlineExamStatus: NetworkOnlineExamStatus): Response<Unit>
}