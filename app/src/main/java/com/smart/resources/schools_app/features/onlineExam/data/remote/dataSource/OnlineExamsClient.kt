package com.smart.resources.schools_app.features.onlineExam.data.remote.dataSource

import com.smart.resources.schools_app.features.onlineExam.data.remote.model.NetworkExamFinishedStatus
import com.smart.resources.schools_app.features.onlineExam.data.remote.model.NetworkOnlineExam
import com.smart.resources.schools_app.features.onlineExam.data.remote.model.NetworkOnlineExamStatus
import retrofit2.Response
import retrofit2.http.*

interface OnlineExamsClient {
    companion object{
        const val EXAM_ID_PATH= "id"
    }

    @GET("onlineExams")
    suspend fun getOnlineExams(): Response<List<NetworkOnlineExam>>

    @POST("addOnlineExam")
    suspend fun addOnlineExam(@Body networkOnlineExam: NetworkOnlineExam): Response<Unit>

    @PUT("examFinish/{$EXAM_ID_PATH}")
    suspend fun finishExam(@Path(EXAM_ID_PATH) examId:String, @Body networkExamFinishedStatus: NetworkExamFinishedStatus): Response<Unit>

    @PUT("updateStatus/{$EXAM_ID_PATH}")
    suspend fun updateStatus(@Path(EXAM_ID_PATH) examId:String, @Body networkOnlineExamStatus: NetworkOnlineExamStatus): Response<Unit>
}