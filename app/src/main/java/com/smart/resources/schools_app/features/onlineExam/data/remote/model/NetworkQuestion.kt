package com.smart.resources.schools_app.features.onlineExam.data.remote.model

data class NetworkQuestion(
    val id: Long?=null,
    val onlineExamId: Long?,
    val typeId: Int?,
    val question: String?,
    val optionOne: String?=null,
    val optionTwo: String?=null,
    val optionThree: String?=null
)