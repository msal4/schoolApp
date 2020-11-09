package com.smart.resources.schools_app.features.onlineExam.domain.usecase.answers

import com.smart.resources.schools_app.features.onlineExam.domain.model.BaseAnswer
import com.smart.resources.schools_app.features.onlineExam.domain.repository.IAnswersRepository
import com.smart.resources.schools_app.features.onlineExam.domain.usecase.ISaveAnswerLocallyUseCase
import com.smart.resources.schools_app.features.users.domain.usecase.IGetCurrentUserIdUseCase
import javax.inject.Inject

class SaveAnswerLocallyUseCase @Inject constructor(
    private val getUserIdUseCase: IGetCurrentUserIdUseCase,
    private val answersRepository: IAnswersRepository
) : ISaveAnswerLocallyUseCase {
    override suspend fun invoke(answer: BaseAnswer, questionId: String) {
        val userId= getUserIdUseCase()
        answersRepository.saveAnswerLocally(answer, questionId, userId)
    }
}