package com.smart.resources.schools_app.features.onlineExam.domain.model.questions

data class CorrectnessQuestion(
    override val id:String,
    override val text:String,
    override var answer:Boolean?=null,
    var correctAnswer:String=""
):BaseQuestion<Boolean?>{
    override val isAnswered get() = answer != null
}

