package com.smart.resources.schools_app.features.onlineExam.data.mappers

import com.smart.resources.schools_app.features.onlineExam.data.model.LocalOnlineExam
import com.smart.resources.schools_app.features.onlineExam.domain.model.OnlineExam

fun mapOnlineExamToLocal(input: OnlineExam):LocalOnlineExam{
    return LocalOnlineExam(
        onlineExamId = input.id,
        subjectName = input.subjectName,
        date = input.examDate,
        examDuration = input.examDuration,
        numberOfQuestions = input.numberOfQuestions,
        examStatus = input.examStatus,
    )
}
