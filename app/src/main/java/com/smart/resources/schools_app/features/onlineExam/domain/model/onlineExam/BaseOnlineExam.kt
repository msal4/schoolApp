package com.smart.resources.schools_app.features.onlineExam.domain.model.onlineExam

import android.os.Parcelable
import com.smart.resources.schools_app.core.typeConverters.room.OnlineExamStatus
import kotlinx.android.parcel.Parcelize
import org.threeten.bp.Duration
import org.threeten.bp.LocalDateTime


abstract class BaseOnlineExam{
    abstract val subjectName: String
    abstract val examDate: LocalDateTime
    abstract val examDuration: Duration
    abstract val numberOfQuestions: Int
    abstract val examStatus: OnlineExamStatus
}

