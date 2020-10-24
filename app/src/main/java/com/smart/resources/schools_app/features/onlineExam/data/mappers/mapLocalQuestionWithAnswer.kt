package com.smart.resources.schools_app.features.onlineExam.data.mappers

import com.smart.resources.schools_app.features.onlineExam.data.local.model.LocalAnswer
import com.smart.resources.schools_app.features.onlineExam.data.local.model.LocalQuestion
import com.smart.resources.schools_app.features.onlineExam.data.local.model.QuestionWithAnswer
import com.smart.resources.schools_app.features.onlineExam.domain.model.BaseAnswer
import com.smart.resources.schools_app.features.onlineExam.domain.model.BaseAnswerableQuestion
import com.smart.resources.schools_app.features.onlineExam.domain.model.Question

fun mapLocalQuestionWithAnswer(
    input: QuestionWithAnswer,
    localQuestionMapper: (LocalQuestion) -> Question,
    localAnswerMapper: (LocalAnswer) -> BaseAnswer,
): BaseAnswerableQuestion {
    return BaseAnswerableQuestion.fromQuestionAnswer(
        question = localQuestionMapper(input.localQuestion),
        answer = if(input.localAnswer== null) null else localAnswerMapper(input.localAnswer)
    )
}