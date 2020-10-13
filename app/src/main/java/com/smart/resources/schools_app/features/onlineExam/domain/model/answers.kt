package com.smart.resources.schools_app.features.onlineExam.domain.model

sealed class BaseAnswer<out AnswerType> (
    open val answer: AnswerType,
){
    val isEmptyAnswer:Boolean get() = this is Answer && answer.isBlank()
}

data class CorrectnessAnswer(
    override var answer:Boolean,
    val correctAnswer:String=""
): BaseAnswer<Boolean>(answer)


data class MultiChoiceAnswer(
    override var answer:Int,
): BaseAnswer<Int>(answer)

data class Answer(
    override var answer:String,
): BaseAnswer<String>(answer)
