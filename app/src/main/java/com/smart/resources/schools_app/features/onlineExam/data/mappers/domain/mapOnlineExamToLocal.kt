package com.smart.resources.schools_app.features.onlineExam.data.mappers.domain

import com.smart.resources.schools_app.features.onlineExam.data.local.model.LocalOnlineExam
import com.smart.resources.schools_app.features.onlineExam.domain.model.OnlineExam

fun mapOnlineExamToLocal(input: OnlineExam):LocalOnlineExam{
    return LocalOnlineExam(
        onlineExamId = input.id,
        userId = "5", // TODO: add this
        subjectName = input.subjectName,
        date = input.examDate,
        examDuration = input.examDuration,
        numberOfQuestions = input.numberOfQuestions,
        examStatus = input.examStatus,
    )
}
