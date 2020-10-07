package com.smart.resources.schools_app.features.onlineExam.domain.usecase

import com.hadiyarajesh.flower.Resource
import com.smart.resources.schools_app.features.onlineExam.domain.repository.IOnlineExamsRepository
import javax.inject.Inject

// TODO: remove if not neededd
class AddOnlineExamByKeyUseCase @Inject constructor(
    private val onlineExamsRepository: IOnlineExamsRepository,
) : IAddOnlineExamByKeyUseCase {
    override suspend fun invoke(examKey: String): Resource<Unit> {
        return onlineExamsRepository.addOnlineExamByKey(examKey)
    }
}