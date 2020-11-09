package com.smart.resources.schools_app.features.onlineExam.domain.usecase.answers

import com.hadiyarajesh.flower.ApiResponse
import com.smart.resources.schools_app.features.onlineExam.domain.model.BaseAnswer
import com.smart.resources.schools_app.features.onlineExam.domain.repository.IAnswersRepository
import com.smart.resources.schools_app.features.onlineExam.domain.usecase.ISendAnswersUseCase
import com.smart.resources.schools_app.features.users.domain.usecase.IGetCurrentUserIdUseCase
import javax.inject.Inject

class SendAnswersUseCase @Inject constructor(
    private val getUserIdUseCase: IGetCurrentUserIdUseCase,
    private val answersRepository: IAnswersRepository
) : ISendAnswersUseCase {
    override suspend fun invoke(answers: List<BaseAnswer>, questionIds: List<String>) : ApiResponse<Unit>{
        val userId = getUserIdUseCase()
        return answersRepository.sendAnswers(answers, questionIds, userId)
    }
}