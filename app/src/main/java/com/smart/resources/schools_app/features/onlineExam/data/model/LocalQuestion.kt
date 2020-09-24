package com.smart.resources.schools_app.features.onlineExam.data.model

data class LocalQuestion(
    private val questionId:String,
    private val questionText:String,
    private val questionType:Int,
    private val options:List<Int>?,
)