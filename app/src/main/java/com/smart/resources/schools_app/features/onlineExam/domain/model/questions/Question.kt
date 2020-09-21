package com.smart.resources.schools_app.features.onlineExam.domain.model.questions

data class Question(
    override val id:String,
    override val text:String,
    override var answer:String= ""
):BaseQuestion<String>{
    override val isAnswered get() = answer.isNotBlank()
}