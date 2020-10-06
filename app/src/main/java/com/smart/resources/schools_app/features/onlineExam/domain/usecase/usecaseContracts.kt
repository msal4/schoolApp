package com.smart.resources.schools_app.features.onlineExam.domain.usecase

import com.hadiyarajesh.flower.ApiResponse
import com.hadiyarajesh.flower.Resource
import com.smart.resources.schools_app.features.onlineExam.domain.model.onlineExam.CompleteOnlineExam
import com.smart.resources.schools_app.features.onlineExam.domain.model.onlineExam.OnlineExam
import kotlinx.coroutines.flow.Flow

interface IGetOnlineExamsUseCase{
   operator fun invoke(): Flow<Resource<List<OnlineExam>>>
}

interface IAddOnlineExamUseCase{
   suspend operator fun invoke(completeOnlineExam: CompleteOnlineExam): ApiResponse<Unit>
}

interface IAddOnlineExamByKeyUseCase{
    suspend operator fun invoke(examKey:String): Resource<Unit>
}


interface IRemoveOnlineExamUseCase{
    suspend operator fun invoke(examId:String): ApiResponse<Unit>
}