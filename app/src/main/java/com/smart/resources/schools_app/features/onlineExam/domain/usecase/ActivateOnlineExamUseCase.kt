package com.smart.resources.schools_app.features.onlineExam.domain.usecase

import com.hadiyarajesh.flower.ApiResponse
import com.smart.resources.schools_app.features.onlineExam.data.repository.OnlineExamsRepository
import com.smart.resources.schools_app.features.onlineExam.domain.model.Question
import com.smart.resources.schools_app.features.onlineExam.domain.repository.IOnlineExamsRepository
import com.smart.resources.schools_app.features.onlineExam.domain.repository.IQuestionsRepository
import javax.inject.Inject

class ActivateOnlineExamUseCase @Inject constructor(
    private val onlineExamsRepository: IOnlineExamsRepository
) : IActivateOnlineExamUseCase {
    override suspend fun invoke(examId: String): ApiResponse<Unit> {
        return onlineExamsRepository.activateOnlineExam(examId)
    }

}