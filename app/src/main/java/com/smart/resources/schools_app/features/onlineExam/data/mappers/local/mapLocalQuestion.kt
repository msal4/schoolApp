package com.smart.resources.schools_app.features.onlineExam.data.mappers.local

import com.smart.resources.schools_app.features.onlineExam.data.model.LocalQuestion
import com.smart.resources.schools_app.features.onlineExam.domain.model.Question

fun mapLocalQuestion(input: LocalQuestion):Question{
    return Question(
        id = input.questionId,
        question = input.questionText,
        options = input.options.orEmpty(),
        questionType = input.questionType,
    )
}