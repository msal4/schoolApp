package com.smart.resources.schools_app.features.onlineExam.data.mappers.questions

import com.smart.resources.schools_app.features.onlineExam.data.local.model.LocalQuestion
import com.smart.resources.schools_app.features.onlineExam.data.remote.model.NetworkQuestion
import com.smart.resources.schools_app.features.onlineExam.domain.model.Question

fun mapQuestionToNetwork(input: Question, examId:String):NetworkQuestion{
    // TODO: complete options
    return NetworkQuestion(
        onlineExamId = examId.toLong(),
        typeId = input.questionType.value, // TODO: complete mapping
        question = input.question,
    )
}