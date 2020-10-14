package com.smart.resources.schools_app.features.onlineExam.domain.model

import com.smart.resources.schools_app.core.typeConverters.room.QuestionType

sealed class BaseAnswerableQuestion<out AnswerType> constructor(
    open val question: Question,
    open val answer: BaseAnswer<AnswerType>?,
) {
    val id: String get() = question.id

    companion object {
        fun fromQuestionAnswer(question: Question, answer: BaseAnswer<Any>?) =
            when (question.questionType) {
                QuestionType.MULTI_CHOICE -> MultiChoiceAnswerableQuestion(
                    question = question,
                    answer = answer as? MultiChoiceAnswer
                )
                QuestionType.CORRECTNESS -> CorrectnessAnswerableQuestion(
                    question = question,
                    answer = answer as? CorrectnessAnswer
                )
                else -> AnswerableQuestion(question = question, answer = answer as? Answer)
            }
    }
}

data class AnswerableQuestion constructor(
    override val question: Question,
    override val answer: Answer?
) : BaseAnswerableQuestion<String>(question, answer) {

    constructor(
        questionId: String,
        question: String,
        options: List<String> = listOf(),
        answer: String? = null
    ) : this(
        question = Question(
            questionId,
            question,
            options,
            QuestionType.DEFINE,
        ),
        answer = if (answer != null) Answer(answer) else null
    )
}

data class MultiChoiceAnswerableQuestion constructor(
    override val question: Question,
    override val answer: MultiChoiceAnswer?

) : BaseAnswerableQuestion<Int>(question, answer) {
    constructor(
        questionId: String,
        question: String,
        options: List<String> = listOf(),
        answer: Int? = null
    ) : this(
        question = Question(
            questionId,
            question,
            options,
            QuestionType.MULTI_CHOICE,
        ),
        answer = if (answer != null) MultiChoiceAnswer(answer) else null
    )
}

data class CorrectnessAnswerableQuestion constructor(
    override val question: Question,
    override val answer: CorrectnessAnswer?

) : BaseAnswerableQuestion<Boolean>(question, answer) {
    constructor(
        questionId: String,
        question: String,
        options: List<String> = listOf(),
        answer: Boolean? = null,
        correctAnswer: String = ""
    ) : this(
        question = Question(
            questionId,
            question,
            options,
            QuestionType.CORRECTNESS,
        ),
        answer = if (answer != null) CorrectnessAnswer(answer, correctAnswer) else null
    )
}