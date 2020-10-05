package com.smart.resources.schools_app.features.onlineExam.data.mappers.answers

import com.smart.resources.schools_app.features.onlineExam.data.local.model.LocalAnswer
import com.smart.resources.schools_app.features.onlineExam.domain.model.Answer
import com.smart.resources.schools_app.features.onlineExam.domain.model.BaseAnswer
import com.smart.resources.schools_app.features.onlineExam.domain.model.CorrectnessAnswer
import com.smart.resources.schools_app.features.onlineExam.domain.model.MultiChoiceAnswer

fun mapLocalAnswer(input: LocalAnswer): BaseAnswer<out Any> {
    return when {
        input.answerCorrectness != null -> {
            CorrectnessAnswer(
                answer = input.answerCorrectness,
                correctAnswer = input.correctAnswer,
            )
        }
        input.answerMultiChoice != null -> {
            MultiChoiceAnswer(
                answer = input.answerMultiChoice,
            )
        }
        else -> {
            Answer(
                answer = input.answerNormal.toString(),
            )
        }
    }
}