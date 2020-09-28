package com.smart.resources.schools_app.features.onlineExam.domain.usecase

import com.hadiyarajesh.flower.Resource
import com.smart.resources.schools_app.features.onlineExam.domain.model.OnlineExam
import kotlinx.coroutines.flow.Flow

interface IGetOnlineExamsUseCase{
   operator fun invoke(): Flow<Resource<List<OnlineExam>>>
}

interface IAddOnlineExamsUseCase{
    suspend operator fun invoke(vararg onlineExam: OnlineExam): Resource<Unit>
}

interface IRemoveOnlineExamUseCase{
    suspend operator fun invoke(examId:String): Resource<Unit>
}