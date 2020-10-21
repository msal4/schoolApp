package com.smart.resources.schools_app.features.onlineExam.data.mappers.answers

import com.smart.resources.schools_app.features.onlineExam.data.remote.model.NetworkAnswer
import com.smart.resources.schools_app.features.onlineExam.domain.model.Answer
import com.smart.resources.schools_app.features.onlineExam.domain.model.BaseAnswer
import com.smart.resources.schools_app.features.onlineExam.domain.model.CorrectnessAnswer
import com.smart.resources.schools_app.features.onlineExam.domain.model.MultiChoiceAnswer

fun mapNetworkAnswer(input: NetworkAnswer): BaseAnswer {
    return when {
        input.chosen != null -> {
            MultiChoiceAnswer(
                answer = input.answer.toString(),
                multiChoiceAnswer = input.chosen,
            )
        }
        input.TF != null -> {
            CorrectnessAnswer(
                answer = input.answer.toString(),
                correctnessAnswer = input.TF,
                correctAnswer = input.answer.toString(),
            )
        }
        else -> {
            Answer(
                answer = input.answer.toString(),
            )
        }
    }
}