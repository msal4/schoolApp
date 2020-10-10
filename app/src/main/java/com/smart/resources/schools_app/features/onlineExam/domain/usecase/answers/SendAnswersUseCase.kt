package com.smart.resources.schools_app.features.onlineExam.domain.usecase.answers

import com.smart.resources.schools_app.features.onlineExam.domain.model.BaseAnswer
import com.smart.resources.schools_app.features.onlineExam.domain.repository.IAnswersRepository
import com.smart.resources.schools_app.features.onlineExam.domain.usecase.ISendAnswersUseCase
import com.smart.resources.schools_app.features.users.domain.usecase.IGetUserIdUseCase
import javax.inject.Inject

class SendAnswersUseCase @Inject constructor(
    private val getUserIdUseCase: IGetUserIdUseCase,
    private val answersRepository: IAnswersRepository
) : ISendAnswersUseCase {
    override suspend fun invoke(answers: List<BaseAnswer<Any>>, questionIds: List<String>) {
        val userId = getUserIdUseCase()
        answersRepository.sendAnswers(answers, questionIds, userId)
    }
}