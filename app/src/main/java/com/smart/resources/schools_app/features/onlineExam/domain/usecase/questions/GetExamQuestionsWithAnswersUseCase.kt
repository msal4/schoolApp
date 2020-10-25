package com.smart.resources.schools_app.features.onlineExam.domain.usecase.questions

import com.hadiyarajesh.flower.Resource
import com.smart.resources.schools_app.features.onlineExam.domain.repository.IAnswersRepository
import com.smart.resources.schools_app.features.onlineExam.domain.usecase.IGetExamQuestionsUseCase
import com.smart.resources.schools_app.features.onlineExam.domain.usecase.IGetExamQuestionsWithAnswersUseCase
import com.smart.resources.schools_app.features.onlineExam.domain.viewModel.ListOfAnswerableQuestions
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class GetExamQuestionsWithAnswersUseCase @Inject constructor(
    private val getExamQuestionsUseCase: IGetExamQuestionsUseCase,
    private val answersRepository: IAnswersRepository
) : IGetExamQuestionsWithAnswersUseCase {
    override fun invoke(
        examId: String,
        userId: String,
        shouldFetchFromRemote: Boolean
    ): Flow<Resource<ListOfAnswerableQuestions>> {
        return flow {
            emit(Resource.loading(null))

            getExamQuestionsUseCase(examId)
//                .distinctUntilChangedBy { it.data }
//                .filter { it.data != null }
                .filter {it.status!= Resource.Status.LOADING}
                .collect {

                    val answerableQuestionsFlow = answersRepository.getStudentExamAnswers(
                        examId,
                        userId,
                        shouldFetchFromRemote
                    )
                    emitAll(answerableQuestionsFlow)
                }
        }
    }

}