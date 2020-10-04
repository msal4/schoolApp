package com.smart.resources.schools_app.features.onlineExam.data.mappers.onlineExams

import com.smart.resources.schools_app.core.typeConverters.room.OnlineExamStatus
import com.smart.resources.schools_app.features.onlineExam.data.local.model.LocalOnlineExam
import com.smart.resources.schools_app.features.onlineExam.data.remote.model.NetworkOnlineExam
import com.smart.resources.schools_app.features.onlineExam.domain.model.OnlineExam
import org.threeten.bp.Duration
import org.threeten.bp.LocalDateTime

fun mapNetworkOnlineExams(input: NetworkOnlineExam):OnlineExam{
    return OnlineExam(
        id = input.idOnlineExam.toString(),
        subjectName = "subjectName${input.idOnlineExam}", // TODO get from backend
        examDate = LocalDateTime.of(input.date, input.time),
        examDuration = Duration.ofMinutes(input.examTime?.toLong()?:0),
        numberOfQuestions = input.nuOfRequiredQuestion?:0,
        examStatus = input.getExamStatus(),
    )
}


fun NetworkOnlineExam.getExamStatus(): OnlineExamStatus {
    return when {
        isFinished == 1 -> {
            OnlineExamStatus.COMPLETED
        }
        status == 1 -> {
            OnlineExamStatus.ACTIVE
        }
        else -> {
            OnlineExamStatus.INACTIVE
        }
    }
}