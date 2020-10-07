package com.smart.resources.schools_app.features.onlineExam.data.mappers.questions

import android.net.Network
import com.smart.resources.schools_app.core.typeConverters.room.OnlineExamStatus
import com.smart.resources.schools_app.core.typeConverters.room.QuestionType
import com.smart.resources.schools_app.features.onlineExam.data.local.model.LocalQuestion
import com.smart.resources.schools_app.features.onlineExam.data.remote.model.NetworkQuestion
import com.smart.resources.schools_app.features.onlineExam.domain.model.Question

fun mapNetworkQuestion(input: NetworkQuestion):Question{
    val options= listOfNotNull(input.optionOne, input.optionTwo, input.optionThree)
    val questionType= QuestionType.fromValue((input.typeId?:3)-1)

    return Question(
        id = input.id.toString(),
        question = input.question?:"",
        options = options,
        questionType = questionType,
    )
}