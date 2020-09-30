package com.smart.resources.schools_app.features.onlineExam.domain.model

import com.smart.resources.schools_app.core.typeConverters.room.QuestionType

data class Question (
    val id:String,
    val question:String,
    val options:List<String> = listOf(),
    val questionType: QuestionType
)

