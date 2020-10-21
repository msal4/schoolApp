package com.smart.resources.schools_app.features.onlineExam.domain.model

import com.smart.resources.schools_app.core.typeConverters.room.QuestionType

sealed class BaseAnswerableQuestion constructor(
    open val question: Question,
    open val answer: BaseAnswer?,
) {
    val id: String get() = question.id

    companion object {
        fun fromQuestionAnswer(question: Question, answer: BaseAnswer?) =
            when (question.questionType) {
                QuestionType.MULTI_CHOICE -> MultiChoiceAnswerableQuestion(
                    question = question,
                    answer = if(answer == null) null else answer as? MultiChoiceAnswer?: MultiChoiceAnswer(answer.answer)
                )
                QuestionType.CORRECTNESS -> CorrectnessAnswerableQuestion(
                    question = question,
                    answer = if(answer == null) null else  answer as? CorrectnessAnswer?: CorrectnessAnswer(answer.answer)
                )
                else -> AnswerableQuestion(question = question, answer = answer as? Answer)
            }
    }
}

data class AnswerableQuestion constructor(
    override val question: Question,
    override val answer: Answer?
) : BaseAnswerableQuestion(question, answer)

data class MultiChoiceAnswerableQuestion constructor(
    override val question: Question,
    override val answer: MultiChoiceAnswer?,
) : BaseAnswerableQuestion(question, answer)

data class CorrectnessAnswerableQuestion constructor(
    override val question: Question,
    override val answer: CorrectnessAnswer?,
) : BaseAnswerableQuestion(question, answer)