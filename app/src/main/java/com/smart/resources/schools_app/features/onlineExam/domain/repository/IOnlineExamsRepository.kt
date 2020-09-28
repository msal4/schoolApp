package com.smart.resources.schools_app.features.onlineExam.domain.repository

import com.hadiyarajesh.flower.Resource
import com.smart.resources.schools_app.features.onlineExam.domain.model.OnlineExam
import kotlinx.coroutines.flow.Flow

interface IOnlineExamsRepository {
    fun getOnlineExams(userId:String): Flow<Resource<List<OnlineExam>>>
    suspend fun addOnlineExams(vararg onlineExam: OnlineExam): Resource<Unit>
    suspend fun removeOnlineExam(examId:String): Resource<Unit>
}