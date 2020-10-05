package com.smart.resources.schools_app.features.onlineExam.data.mappers.onlineExams

import com.smart.resources.schools_app.features.onlineExam.data.remote.model.NetworkOnlineExam
import com.smart.resources.schools_app.features.onlineExam.domain.model.onlineExam.AddOnlineExam

fun mapAddOnlineExamToNetwork(input: AddOnlineExam):NetworkOnlineExam{
    // TODO: add different model for post
    return NetworkOnlineExam(
        idOnlineExam = null,
        teacherId =  null,
        examKey = "some key",
        date = input.examDate.toLocalDate(),
        time = input.examDate.toLocalTime(),
        examTime = input.examDuration.toMinutes(),
        nuOfRequiredQuestion = 5, // TODO: add
        status = 0,
        isFinished = 0,
    )
}
