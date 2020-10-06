package com.smart.resources.schools_app.features.onlineExam.data.mappers.questions

import com.smart.resources.schools_app.features.onlineExam.data.local.model.LocalQuestion
import com.smart.resources.schools_app.features.onlineExam.data.remote.model.NetworkQuestion
import com.smart.resources.schools_app.features.onlineExam.domain.model.Question

fun mapQuestionToNetwork(input: Question, examId:String):NetworkQuestion{
    return NetworkQuestion(
        onlineExamId = examId.toLongOrNull(),
        typeId = input.questionType.value,
        question = input.question,
        optionOne = input.options.getOrNull(0),
        optionTwo = input.options.getOrNull(1),
        optionThree = input.options.getOrNull(2)
    )
}