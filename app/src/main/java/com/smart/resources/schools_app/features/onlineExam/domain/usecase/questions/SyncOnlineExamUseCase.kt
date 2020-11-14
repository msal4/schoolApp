package com.smart.resources.schools_app.features.onlineExam.domain.usecase.questions

import com.hadiyarajesh.flower.ApiResponse
import com.smart.resources.schools_app.features.onlineExam.domain.repository.IOnlineExamsRepository
import com.smart.resources.schools_app.features.onlineExam.domain.usecase.ISyncOnlineExamUseCase
import javax.inject.Inject

class SyncOnlineExamUseCase @Inject constructor(
    private val onlineExamsRepository: IOnlineExamsRepository,
) : ISyncOnlineExamUseCase {
    override suspend fun invoke(examId: String): ApiResponse<Unit> {
        return onlineExamsRepository.syncOnlineExam(examId)
    }

}