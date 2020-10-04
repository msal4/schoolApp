package com.smart.resources.schools_app.features.onlineExam.domain.usecase

import com.hadiyarajesh.flower.Resource
import com.smart.resources.schools_app.features.onlineExam.domain.model.CompleteOnlineExam
import com.smart.resources.schools_app.features.onlineExam.domain.repository.IOnlineExamsRepository
import com.smart.resources.schools_app.features.onlineExam.domain.repository.IQuestionsRepository
import com.smart.resources.schools_app.features.users.data.UserRepository
import javax.inject.Inject

class AddOnlineExamsUseCase @Inject constructor(
    private val onlineExamsRepository: IOnlineExamsRepository,
    private val questionsRepository: IQuestionsRepository,
    private val userRepository: UserRepository,
    ) : IAddOnlineExamsUseCase {
    override suspend operator fun invoke(vararg completeOnlineExam: CompleteOnlineExam): Resource<Unit> {
        val examDetails = completeOnlineExam.map { it.onlineExam }.toTypedArray()

        // 1. add exam
        val userId= userRepository.getCurrentUserAccount()?.originalId.toString() // TODO: change this to usecase
        onlineExamsRepository.addOnlineExams(userId, *examDetails)

        // 2. add exam questions
        // this must be done second since it is linked to the exam with Foreign key
        completeOnlineExam.forEach {
            questionsRepository.addQuestions(examId = it.onlineExam.id, *(it.questions.toTypedArray()))
        }

        // TODO: correct this

        return Resource.success(Unit)
    }
}