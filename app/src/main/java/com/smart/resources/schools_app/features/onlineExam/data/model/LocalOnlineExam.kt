package com.smart.resources.schools_app.features.onlineExam.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.smart.resources.schools_app.core.typeConverters.room.OnlineExamStatus
import org.threeten.bp.Duration
import org.threeten.bp.LocalDateTime

@Entity(
    tableName = LocalOnlineExam.TABLE_NAME,
)
data class LocalOnlineExam(
    @PrimaryKey
    val onlineExamId: String,
    val subjectName: String,
    val date: LocalDateTime,
    val examDuration: Duration,
    val startDateTime: LocalDateTime? = null, // TODO: delete or keep?,this does not exists in domain model
    val numberOfQuestions: Int,
    val examStatus: OnlineExamStatus,
) {
    companion object {
        const val TABLE_NAME = "OnlineExams"
    }
}