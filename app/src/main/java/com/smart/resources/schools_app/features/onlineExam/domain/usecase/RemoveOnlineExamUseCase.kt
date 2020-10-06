package com.smart.resources.schools_app.features.onlineExam.domain.usecase

import com.hadiyarajesh.flower.ApiResponse
import com.hadiyarajesh.flower.Resource
import com.smart.resources.schools_app.features.onlineExam.domain.repository.IOnlineExamsRepository
import javax.inject.Inject

class RemoveOnlineExamUseCase @Inject constructor(
    private val onlineExamsRepository: IOnlineExamsRepository,
) : IRemoveOnlineExamUseCase {
    override suspend operator fun invoke(examId:String): ApiResponse<Unit> {
        return onlineExamsRepository.removeOnlineExam(examId)
    }
}