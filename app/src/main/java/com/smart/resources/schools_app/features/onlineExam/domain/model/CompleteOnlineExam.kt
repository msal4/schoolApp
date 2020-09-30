package com.smart.resources.schools_app.features.onlineExam.domain.model

import android.os.Parcelable
import com.smart.resources.schools_app.core.typeConverters.room.OnlineExamStatus
import kotlinx.android.parcel.Parcelize
import org.threeten.bp.Duration
import org.threeten.bp.LocalDateTime

data class CompleteOnlineExam(
    val onlineExam: OnlineExam,
    val questions:List<Question>
)


