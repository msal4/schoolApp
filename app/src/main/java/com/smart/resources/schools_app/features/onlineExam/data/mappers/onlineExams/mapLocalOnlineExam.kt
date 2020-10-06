package com.smart.resources.schools_app.features.onlineExam.data.mappers.onlineExams

import com.smart.resources.schools_app.features.onlineExam.data.local.model.LocalOnlineExam
import com.smart.resources.schools_app.features.onlineExam.domain.model.onlineExam.OnlineExam

fun mapLocalOnlineExam(input: LocalOnlineExam): OnlineExam {
    return OnlineExam(
        id = input.onlineExamId,
        subjectName = input.subjectName,
        examDate = input.date,
        examDuration = input.examDuration,
        numberOfRequiredQuestions = input.numberOfRequiredQuestions,
        examStatus = input.examStatus,
    )
}