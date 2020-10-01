package com.smart.resources.schools_app.features.onlineExam.data.remote.model

data class NetworkQuestion(
    val id: Int?,
    val onlineExamId: Int?,
    val typeId: Int?,
    val question: String?,
    val optionOne: String?,
    val optionTwo: String?,
    val optionThree: String?
)