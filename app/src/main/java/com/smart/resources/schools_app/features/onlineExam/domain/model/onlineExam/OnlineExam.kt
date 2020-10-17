package com.smart.resources.schools_app.features.onlineExam.domain.model.onlineExam

import android.os.Parcelable
import com.smart.resources.schools_app.core.typeConverters.room.OnlineExamStatus
import kotlinx.android.parcel.Parcelize
import org.threeten.bp.Duration
import org.threeten.bp.LocalDateTime

@Parcelize
data class OnlineExam(
    val id: String,
    val numberOfQuestions: Int,
    override val subjectName: String,
    override val examDate: LocalDateTime,
    override val examDuration: Duration,
    override val numberOfRequiredQuestions: Int,
    override val examStatus: OnlineExamStatus,
) : BaseOnlineExam(), Parcelable {

    val remainingDuration: Duration
        get() {
            val passedDuration = Duration.between(examDate, LocalDateTime.now())
            val tempRemainingDuration = (examDuration - passedDuration)
            return if (tempRemainingDuration.isNegative) Duration.ZERO else tempRemainingDuration
        }
}

