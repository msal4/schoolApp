package com.smart.resources.schools_app.features.onlineExam.domain.usecase

import com.hadiyarajesh.flower.Resource
import com.smart.resources.schools_app.features.onlineExam.domain.model.OnlineExam
import com.smart.resources.schools_app.features.onlineExam.domain.repository.IOnlineExamsRepository
import javax.inject.Inject

class AddOnlineExamsUseCase @Inject constructor(
    private val onlineExamsRepository: IOnlineExamsRepository,
) : IAddOnlineExamsUseCase {
    override suspend operator fun invoke(vararg onlineExam: OnlineExam): Resource<Unit> {
        return onlineExamsRepository.addOnlineExams(*onlineExam)
    }
}