package com.smart.resources.schools_app.features.onlineExam.domain.usecase

import com.hadiyarajesh.flower.Resource
import com.smart.resources.schools_app.features.onlineExam.domain.model.CompleteOnlineExam
import com.smart.resources.schools_app.features.onlineExam.domain.repository.IOnlineExamsRepository
import com.smart.resources.schools_app.features.onlineExam.domain.repository.IQuestionsRepository
import javax.inject.Inject

class AddOnlineExamByKeyUseCase @Inject constructor(
    private val onlineExamsRepository: IOnlineExamsRepository,
) : IAddOnlineExamByKeyUseCase {
    override suspend fun invoke(examKey: String): Resource<Unit> {
        return onlineExamsRepository.addOnlineExamByKey(examKey)
    }
}