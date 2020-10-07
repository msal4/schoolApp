package com.smart.resources.schools_app.features.onlineExam.domain.usecase

import com.hadiyarajesh.flower.ApiResponse
import com.smart.resources.schools_app.features.onlineExam.domain.repository.IOnlineExamsRepository
import com.smart.resources.schools_app.features.onlineExam.domain.repository.IQuestionsRepository
import javax.inject.Inject

class FinishOnlineExamUseCase @Inject constructor(
    private val onlineExamsRepository: IOnlineExamsRepository
) : IFinishOnlineExamUseCase {
    override suspend fun invoke(examId: String): ApiResponse<Unit> {
        return onlineExamsRepository.finishOnlineExam(examId)
    }
}