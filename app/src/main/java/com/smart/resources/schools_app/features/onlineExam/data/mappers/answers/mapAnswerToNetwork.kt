package com.smart.resources.schools_app.features.onlineExam.data.mappers.answers

import com.smart.resources.schools_app.features.onlineExam.data.remote.model.PostNetworkAnswer
import com.smart.resources.schools_app.features.onlineExam.domain.model.Answer
import com.smart.resources.schools_app.features.onlineExam.domain.model.BaseAnswer
import com.smart.resources.schools_app.features.onlineExam.domain.model.CorrectnessAnswer
import com.smart.resources.schools_app.features.onlineExam.domain.model.MultiChoiceAnswer

fun mapAnswerToNetwork(answer: BaseAnswer<Any>,  questionId:String): PostNetworkAnswer{
    return when(answer){
        is MultiChoiceAnswer -> PostNetworkAnswer(
            chosen = answer.answer,
            questionId= questionId,
        )
        is CorrectnessAnswer -> PostNetworkAnswer(
            answer = answer.correctAnswer,
            TF = answer.answer,
            questionId= questionId,
        )
        is Answer -> PostNetworkAnswer(
            answer = answer.answer,
            questionId= questionId,
        )
    }
}