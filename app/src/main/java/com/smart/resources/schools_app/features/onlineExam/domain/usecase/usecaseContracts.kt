package com.smart.resources.schools_app.features.onlineExam.domain.usecase

import com.hadiyarajesh.flower.ApiResponse
import com.hadiyarajesh.flower.Resource
import com.smart.resources.schools_app.features.onlineExam.domain.model.Question
import com.smart.resources.schools_app.features.onlineExam.domain.model.onlineExam.CompleteOnlineExam
import com.smart.resources.schools_app.features.onlineExam.domain.model.onlineExam.OnlineExam
import kotlinx.coroutines.flow.Flow

interface IGetUserOnlineExamsUseCase{
   operator fun invoke(): Flow<Resource<List<OnlineExam>>>
}

interface IGetOnlineExamUseCase{
    operator fun invoke(examId:String): Flow<Resource<OnlineExam>>
}

interface IGetOnlineExamQuestionsUseCase{
    operator fun invoke(examId:String): Flow<Resource<List<Question>>>
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

interface IFinishOnlineExamUseCase{
    suspend operator fun invoke(examId:String): ApiResponse<Unit>
}

interface IActivateOnlineExamUseCase{
    suspend operator fun invoke(examId:String): ApiResponse<Unit>
}