package com.smart.resources.schools_app.features.onlineExam.domain.usecase

import com.hadiyarajesh.flower.ApiResponse
import com.hadiyarajesh.flower.ApiSuccessResponse
import com.smart.resources.schools_app.core.extentions.withNewData
import com.smart.resources.schools_app.features.onlineExam.domain.model.onlineExam.CompleteOnlineExam
import com.smart.resources.schools_app.features.onlineExam.domain.repository.IOnlineExamsRepository
import com.smart.resources.schools_app.features.onlineExam.domain.repository.IQuestionsRepository
import com.smart.resources.schools_app.features.users.domain.usecase.IGetUserIdUseCase
import javax.inject.Inject

class AddOnlineExamUseCase @Inject constructor(
    private val onlineExamsRepository: IOnlineExamsRepository,
    private val questionsRepository: IQuestionsRepository,
    private val getUserIdUseCase: IGetUserIdUseCase,
) : IAddOnlineExamUseCase {
    override suspend fun invoke(completeOnlineExam: CompleteOnlineExam): ApiResponse<Unit> {
        // 1. add exam
        val userId = getUserIdUseCase()
        val examsRes = onlineExamsRepository.addOnlineExam(userId, completeOnlineExam.addOnlineExam)

        // 2. add exam questions
        // this must be done second since it is linked to the exam with Foreign key
        if (examsRes is ApiSuccessResponse && examsRes.body != null) {
            questionsRepository.addQuestions(
                examId = examsRes.body?.id ?: "",
                questions = completeOnlineExam.questions
            )
        }

        return examsRes.withNewData { Unit }
    }

}