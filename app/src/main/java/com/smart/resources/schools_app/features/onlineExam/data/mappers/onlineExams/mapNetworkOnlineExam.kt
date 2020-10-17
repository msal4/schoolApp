package com.smart.resources.schools_app.features.onlineExam.data.mappers.onlineExams

import com.smart.resources.schools_app.core.typeConverters.room.OnlineExamStatus
import com.smart.resources.schools_app.features.onlineExam.data.remote.model.onlineExam.NetworkOnlineExam
import com.smart.resources.schools_app.features.onlineExam.domain.model.onlineExam.OnlineExam
import org.threeten.bp.Duration
import org.threeten.bp.LocalDateTime

fun mapNetworkOnlineExam(input: NetworkOnlineExam): OnlineExam {
    return OnlineExam(
        id = input.idOnlineExam.toString(),
        subjectName = input.subjectName.toString(),
        examDate = LocalDateTime.of(input.date, input.time),
        examDuration = Duration.ofMinutes(input.examTime?:0),
        numberOfQuestions = input.availableQuestions?:0,
        numberOfRequiredQuestions = input.nuOfRequiredQuestion?:0,
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