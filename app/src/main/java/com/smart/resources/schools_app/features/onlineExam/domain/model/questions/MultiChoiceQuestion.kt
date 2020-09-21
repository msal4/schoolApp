package com.smart.resources.schools_app.features.onlineExam.domain.model.questions

data class MultiChoiceQuestion(
    override val id:String,
    override val text:String,
    override var answer:Int= -1,
    val choices:List<String>
): BaseQuestion<Int>{
    override val isAnswered get() = answer >= 0 && answer <= choices.size
}

