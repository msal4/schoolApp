package com.smart.resources.schools_app.features.onlineExam.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.threeten.bp.Duration
import org.threeten.bp.LocalDateTime

@Parcelize
data class OnlineExamDetails(
    val id: String,
    val subjectName: String,
    val date: LocalDateTime,
    val examDuration: Duration,
    val startDateTime: LocalDateTime? = null,
    val numberOfQuestions: Int,
    val examStatus: OnlineExamStatus,

    ) : Parcelable {
    val isLocked get() = examStatus == OnlineExamStatus.LOCKED
    val remainingDuration: Duration
        get() {
            val passedDuration = Duration.between(startDateTime, LocalDateTime.now())
            val tempRemainingDuration = (examDuration - passedDuration)
            return if (tempRemainingDuration.isNegative) Duration.ZERO else tempRemainingDuration
        }
}

enum class OnlineExamStatus {
    LOCKED,
    READY,
    IN_PROGRESS,
    COMPLETED
}