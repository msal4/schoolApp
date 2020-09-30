package com.smart.resources.schools_app.features.onlineExam.data.mappers.domain

import com.smart.resources.schools_app.features.onlineExam.data.model.LocalQuestion
import com.smart.resources.schools_app.features.onlineExam.domain.model.Question

fun mapQuestionToLocal(input: Question, examId:String):LocalQuestion{
    return LocalQuestion(
        questionId = input.id,
        onlineExamId = examId,
        questionText = input.question,
        options = input.options,
        questionType = input.questionType,
    )
}