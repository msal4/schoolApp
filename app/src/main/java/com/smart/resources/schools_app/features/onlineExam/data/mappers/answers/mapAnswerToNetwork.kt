package com.smart.resources.schools_app.features.onlineExam.data.mappers.answers

import com.smart.resources.schools_app.features.onlineExam.data.remote.model.PostNetworkAnswer
import com.smart.resources.schools_app.features.onlineExam.domain.model.Answer
import com.smart.resources.schools_app.features.onlineExam.domain.model.BaseAnswer
import com.smart.resources.schools_app.features.onlineExam.domain.model.CorrectnessAnswer
import com.smart.resources.schools_app.features.onlineExam.domain.model.MultiChoiceAnswer

fun mapAnswerToNetwork(answer: BaseAnswer,  questionId:String): PostNetworkAnswer{
    return when(answer){
        is MultiChoiceAnswer -> PostNetworkAnswer(
            answer= answer.answer,
            chosen = answer.multiChoiceAnswer,
            questionId= questionId,
        )
        is CorrectnessAnswer -> PostNetworkAnswer(
            answer = if(answer.correctnessAnswer == true || answer.correctAnswer.isBlank()) answer.answer else answer.correctAnswer,
            TF = answer.correctnessAnswer,
            questionId= questionId,
        )
        is Answer -> PostNetworkAnswer(
            answer = answer.answer,
            questionId= questionId,
        )
    }
}