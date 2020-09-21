package com.smart.resources.schools_app.features.onlineExam.domain.model.questions

interface BaseQuestion<AnswerType> {
    val id:String
    val text:String
    var answer:AnswerType
    val isAnswered:Boolean

    override fun equals(other: Any?) : Boolean
}