package com.smart.resources.schools_app.features.onlineExam.domain.usecase

import com.hadiyarajesh.flower.Resource
import com.smart.resources.schools_app.features.onlineExam.domain.repository.IOnlineExamsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class IsExamKeyCorrectUseCase @Inject constructor(
    private val onlineExamsRepository: IOnlineExamsRepository,
) : IIsExamKeyCorrectUseCase {
    override suspend fun invoke(examId:String, examKey: String): Flow<Resource<Boolean>> {
         val examKeyFlow= onlineExamsRepository.getExamKey(examId)
        return examKeyFlow.map {
            Resource(
                status = it.status,
                data = it.data == examKey,
                message = it.message,
            )
        }
    }
}