package com.smart.resources.schools_app.features.onlineExam.domain.model


sealed class BaseAnswer(
    open val answer:String,
){
    val hasAnswerString:Boolean get() =  answer.isNotBlank()
}

data class Answer(
    override val answer:String,
): BaseAnswer(answer)


data class CorrectnessAnswer(
    override val answer:String,
    val correctnessAnswer:Boolean?=null,
    val correctAnswer:String=""
): BaseAnswer(answer){
    val hasCorrectAnswer:Boolean get() =  correctAnswer.isNotBlank()
}


data class MultiChoiceAnswer(
    override val answer:String,
    val multiChoiceAnswer:Int?=null,
): BaseAnswer(answer)


