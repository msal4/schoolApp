package com.smart.resources.schools_app.features.onlineExam.domain.model

data class Question (
    val id:String,
    val question:String,
    val options:List<String> = listOf(),
    val questionType: QuestionType
)

enum class QuestionType{
    NORMAL,
    CORRECTNESS,
    MULTI_CHOICE
}