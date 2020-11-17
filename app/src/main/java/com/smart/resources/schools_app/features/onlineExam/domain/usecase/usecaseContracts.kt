package com.smart.resources.schools_app.features.onlineExam.domain.usecase

import com.hadiyarajesh.flower.ApiResponse
import com.hadiyarajesh.flower.Resource
import com.smart.resources.schools_app.features.onlineExam.domain.model.BaseAnswer
import com.smart.resources.schools_app.features.onlineExam.domain.model.Question
import com.smart.resources.schools_app.features.onlineExam.domain.model.onlineExam.CompleteOnlineExam
import com.smart.resources.schools_app.features.onlineExam.domain.model.onlineExam.OnlineExam
import com.smart.resources.schools_app.features.onlineExam.domain.viewModel.ListOfAnswerableQuestions
import kotlinx.coroutines.flow.Flow

interface IGetUserOnlineExamsUseCase {
    operator fun invoke(): Flow<Resource<List<OnlineExam>>>
}

interface IGetOnlineExamUseCase {
    operator fun invoke(examId: String): Flow<Resource<OnlineExam>>
}

interface ISyncOnlineExamUseCase {
    suspend operator fun invoke(examId: String): ApiResponse<Unit>
}

interface IGetExamQuestionsUseCase {
    operator fun invoke(examId: String): Flow<Resource<List<Question>>>
}

interface IGetExamQuestionsWithAnswersUseCase {
    operator fun invoke(
        examId: String,
        backendUserId: String,
        shouldFetchFromRemote: Boolean
    ): Flow<Resource<ListOfAnswerableQuestions>>
}

interface IAddOnlineExamUseCase {
    suspend operator fun invoke(completeOnlineExam: CompleteOnlineExam): ApiResponse<Unit>
}

interface IIsExamKeyCorrectUseCase {
    suspend operator fun invoke(examId: String, examKey: String): Flow<Resource<Boolean>>
}

interface IRemoveOnlineExamUseCase {
    suspend operator fun invoke(examId: String): ApiResponse<Unit>
}

interface IFinishOnlineExamUseCase {
    suspend operator fun invoke(examId: String): ApiResponse<Unit>
}

interface IActivateOnlineExamUseCase {
    suspend operator fun invoke(examId: String): ApiResponse<Unit>
}

interface ISaveAnswerLocallyUseCase {
    suspend operator fun invoke(answer: BaseAnswer, questionId: String)
}

interface ISendAnswersUseCase {
    suspend operator fun invoke(
        answers: List<BaseAnswer>,
        questionIds: List<String>
    ): ApiResponse<Unit>
}
