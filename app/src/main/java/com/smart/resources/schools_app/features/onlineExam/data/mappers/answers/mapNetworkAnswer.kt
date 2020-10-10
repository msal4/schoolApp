package com.smart.resources.schools_app.features.onlineExam.data.mappers.answers

import com.smart.resources.schools_app.features.onlineExam.data.remote.model.NetworkAnswer
import com.smart.resources.schools_app.features.onlineExam.domain.model.Answer
import com.smart.resources.schools_app.features.onlineExam.domain.model.BaseAnswer

fun mapNetworkAnswer(input: NetworkAnswer): BaseAnswer<out Any> {
    // TODO: handle no answer type

    return Answer(answer = input.answer.toString())
//    return when {
//        input.answerCorrectness != null -> {
//            CorrectnessAnswer(
//                answer = input.answerCorrectness,
//                correctAnswer = input.correctAnswer,
//            )
//        }
//        input.answerMultiChoice != null -> {
//            MultiChoiceAnswer(
//                answer = input.answerMultiChoice,
//            )
//        }
//        else -> {
//            Answer(
//                answer = input.answerNormal.toString(),
//            )
//        }
//    }
}