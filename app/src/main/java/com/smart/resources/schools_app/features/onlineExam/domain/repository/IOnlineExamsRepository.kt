package com.smart.resources.schools_app.features.onlineExam.domain.repository

import com.hadiyarajesh.flower.ApiResponse
import com.hadiyarajesh.flower.Resource
import com.smart.resources.schools_app.features.onlineExam.domain.model.onlineExam.AddOnlineExam
import com.smart.resources.schools_app.features.onlineExam.domain.model.onlineExam.OnlineExam
import kotlinx.coroutines.flow.Flow

interface IOnlineExamsRepository {
    fun getUserOnlineExams(userId:String, isTeacher:Boolean,): Flow<Resource<List<OnlineExam>>>
    fun getOnlineExam(examId:String): Flow<Resource<OnlineExam>>
    suspend fun syncOnlineExam(examId:String): ApiResponse<Unit>
    suspend fun addOnlineExam(userId: String, addOnlineExam: AddOnlineExam): ApiResponse<OnlineExam>
    suspend fun getExamKey(examId:String): Flow<Resource<String>>
    suspend fun removeOnlineExam(examId:String): ApiResponse<Unit>
    suspend fun activateOnlineExam(examId:String): ApiResponse<Unit>
    suspend fun finishOnlineExam(examId:String): ApiResponse<Unit>
}