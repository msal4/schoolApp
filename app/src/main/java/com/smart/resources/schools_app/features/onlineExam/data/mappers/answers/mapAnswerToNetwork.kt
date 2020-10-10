package com.smart.resources.schools_app.features.onlineExam.data.mappers.answers

import com.smart.resources.schools_app.features.onlineExam.data.remote.model.PostNetworkAnswer
import com.smart.resources.schools_app.features.onlineExam.domain.model.Answer
import com.smart.resources.schools_app.features.onlineExam.domain.model.BaseAnswer
import com.smart.resources.schools_app.features.onlineExam.domain.model.CorrectnessAnswer
import com.smart.resources.schools_app.features.onlineExam.domain.model.MultiChoiceAnswer

fun mapAnswerToNetwork(answer: BaseAnswer<out Any>, userId:String, questionId:String): PostNetworkAnswer{
    return PostNetworkAnswer(
        answer = answer.answer.toString(), // TODO: handle this correctly
        questionId= questionId,
        studentId = userId,
    )
}