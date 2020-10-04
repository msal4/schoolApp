package com.smart.resources.schools_app.features.onlineExam.data.mappers.onlineExams

import com.smart.resources.schools_app.features.onlineExam.data.local.model.LocalOnlineExam
import com.smart.resources.schools_app.features.onlineExam.domain.model.OnlineExam

fun mapOnlineExam(input: OnlineExam):LocalOnlineExam{
    return LocalOnlineExam(
        onlineExamId = input.id,
        subjectName = input.subjectName,
        date = input.examDate,
        examDuration = input.examDuration,
        numberOfQuestions = input.numberOfQuestions,
        examStatus = input.examStatus,
    )
}
