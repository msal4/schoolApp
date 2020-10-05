package com.smart.resources.schools_app.features.onlineExam.domain.model.onlineExam

import android.os.Parcelable
import com.smart.resources.schools_app.core.typeConverters.room.OnlineExamStatus
import kotlinx.android.parcel.Parcelize
import org.threeten.bp.Duration
import org.threeten.bp.LocalDateTime

data class AddOnlineExam(
    val classIds:List<Int>,
    override val subjectName: String,
    override val examDate: LocalDateTime,
    override val examDuration: Duration,
    override val numberOfQuestions: Int,
    override val examStatus: OnlineExamStatus,
) : BaseOnlineExam()

