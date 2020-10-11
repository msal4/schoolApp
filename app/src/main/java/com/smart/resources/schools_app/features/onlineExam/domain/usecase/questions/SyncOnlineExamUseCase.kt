package com.smart.resources.schools_app.features.onlineExam.domain.usecase.questions

import com.hadiyarajesh.flower.ApiResponse
import com.hadiyarajesh.flower.Resource
import com.smart.resources.schools_app.features.onlineExam.domain.model.onlineExam.OnlineExam
import com.smart.resources.schools_app.features.onlineExam.domain.repository.IOnlineExamsRepository
import com.smart.resources.schools_app.features.onlineExam.domain.usecase.ISyncOnlineExamUseCase
import com.smart.resources.schools_app.features.users.data.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SyncOnlineExamUseCase @Inject constructor(
    private val onlineExamsRepository: IOnlineExamsRepository,
) : ISyncOnlineExamUseCase {
    override suspend fun invoke(examId: String): ApiResponse<Unit> {
        return onlineExamsRepository.syncOnlineExam(examId)
    }

}