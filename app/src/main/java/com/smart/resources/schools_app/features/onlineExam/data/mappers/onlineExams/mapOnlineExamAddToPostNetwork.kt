package com.smart.resources.schools_app.features.onlineExam.data.mappers.onlineExams

import com.smart.resources.schools_app.features.onlineExam.data.remote.model.onlineExam.NetworkOnlineExam
import com.smart.resources.schools_app.features.onlineExam.data.remote.model.onlineExam.PostNetworkOnlineExam
import com.smart.resources.schools_app.features.onlineExam.domain.model.onlineExam.AddOnlineExam

fun mapAddOnlineExamToNetwork(input: AddOnlineExam): PostNetworkOnlineExam {
    return PostNetworkOnlineExam(
        classes = input.classIds.map { it.toLong() },
        subjectName = input.subjectName,
        examKey = input.examKey,
        date = input.examDate.toLocalDate(),
        time = input.examDate.toLocalTime(),
        examTime = input.examDuration.toMinutes(),
        nuOfRequiredQuestion = input.numberOfRequiredQuestions,
        status = 0,
        isFinished = 0,
    )
}
