package com.smart.resources.schools_app.features.onlineExam.data.local.mappers

import com.smart.resources.schools_app.features.onlineExam.data.local.model.LocalOnlineExam
import com.smart.resources.schools_app.features.onlineExam.domain.model.OnlineExam

fun mapLocalOnlineExam(input: LocalOnlineExam):OnlineExam{
    return OnlineExam(
        id = input.onlineExamId,
        subjectName = input.subjectName,
        examDate = input.date,
        examDuration = input.examDuration,
        numberOfQuestions = input.numberOfQuestions,
        examStatus = input.examStatus,
    )
}