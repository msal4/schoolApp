package com.smart.resources.schools_app.features.onlineExam.data.mappers.answers

import com.smart.resources.schools_app.features.onlineExam.data.local.model.LocalAnswer
import com.smart.resources.schools_app.features.onlineExam.domain.model.Answer
import com.smart.resources.schools_app.features.onlineExam.domain.model.BaseAnswer
import com.smart.resources.schools_app.features.onlineExam.domain.model.CorrectnessAnswer
import com.smart.resources.schools_app.features.onlineExam.domain.model.MultiChoiceAnswer

fun mapAnswerToLocal(answer: BaseAnswer<Any>, questionId:String, userId:String): LocalAnswer{
    return LocalAnswer(
        userId = userId,
        questionId = questionId,
        answerCorrectness = if (answer is CorrectnessAnswer) answer.answer else null,
        correctAnswer = if (answer is CorrectnessAnswer) answer.correctAnswer else "",
        answerMultiChoice = if (answer is MultiChoiceAnswer) answer.answer else null,
        answerNormal = if (answer is Answer) answer.answer else null,
    )
}