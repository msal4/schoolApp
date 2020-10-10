package com.smart.resources.schools_app.features.onlineExam.domain.usecase.questions

import com.hadiyarajesh.flower.Resource
import com.haytham.coder.extensions.isNotNullOrEmpty
import com.smart.resources.schools_app.core.typeConverters.room.QuestionType
import com.smart.resources.schools_app.features.onlineExam.domain.model.*
import com.smart.resources.schools_app.features.onlineExam.domain.repository.IAnswersRepository
import com.smart.resources.schools_app.features.onlineExam.domain.repository.IQuestionsRepository
import com.smart.resources.schools_app.features.onlineExam.domain.repository.ListOfAnswers
import com.smart.resources.schools_app.features.onlineExam.domain.usecase.IGetExamQuestionsUseCase
import com.smart.resources.schools_app.features.onlineExam.domain.usecase.IGetExamQuestionsWithAnswersUseCase
import com.smart.resources.schools_app.features.onlineExam.domain.usecase.IGetStudentExamAnswersUseCase
import com.smart.resources.schools_app.features.onlineExam.domain.usecase.answers.GetStudentExamAnswersUseCase
import com.smart.resources.schools_app.features.onlineExam.domain.viewModel.ListOfAnswerableQuestions
import com.smart.resources.schools_app.features.users.domain.usecase.IGetUserIdUseCase
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class GetExamQuestionsWithAnswersUseCase @Inject constructor(
    private val getExamQuestionsUseCase: IGetExamQuestionsUseCase,
    private val getStudentExamAnswersUseCase: IGetStudentExamAnswersUseCase,
) : IGetExamQuestionsWithAnswersUseCase {
    override  fun invoke(examId:String, userId:String): Flow<Resource<ListOfAnswerableQuestions>> {
        val questionsFlow= getExamQuestionsUseCase(examId)
        val answersFlow= getStudentExamAnswersUseCase(examId,userId)
         return questionsFlow.combine(answersFlow, ::transformFlows)
    }

    private fun transformFlows(
        questionsResource: Resource<List<Question>>,
        answersResource: Resource<ListOfAnswers>
    ):Resource<ListOfAnswerableQuestions> {
        val questions = questionsResource.data.orEmpty()
        val answers:ListOfAnswers = answersResource.data.orEmpty()
        val answerableQuestions:ListOfAnswerableQuestions = combineQuestionsAndAnswers(questions, answers)

        return when(questionsResource.status){
            Resource.Status.SUCCESS -> {
                when(answersResource.status){
                    Resource.Status.SUCCESS -> {
                        Resource.success(data = answerableQuestions)
                    }
                    Resource.Status.ERROR -> {
                        Resource.error(questionsResource.message.toString(), data = answerableQuestions)
                    }
                    Resource.Status.LOADING -> {
                        Resource.loading(data = answerableQuestions)
                    }
                }
            }
            Resource.Status.LOADING -> Resource.loading()
            Resource.Status.ERROR -> Resource.error(questionsResource.message.toString(),  data = answerableQuestions)
        }
    }

    private fun combineQuestionsAndAnswers(questions:List<Question>, answers: ListOfAnswers?): ListOfAnswerableQuestions{
        return questions.mapIndexed { index, question ->
            when(question.questionType){
                QuestionType.MULTI_CHOICE -> {
                    MultiChoiceAnswerableQuestion(
                        question = question,
                        answer = answers?.getOrNull(index) as? MultiChoiceAnswer
                    )
                }
                QuestionType.CORRECTNESS -> {
                    CorrectnessAnswerableQuestion(
                        question = question,
                        answer = answers?.getOrNull(index) as? CorrectnessAnswer
                    )
                }
                QuestionType.NORMAL -> {
                    AnswerableQuestion(
                        question = question,
                        answer = answers?.getOrNull(index) as? Answer
                    )
                }
            }
        }
    }
}