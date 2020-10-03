package com.smart.resources.schools_app.features.onlineExam.data.local.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.smart.resources.schools_app.core.typeConverters.room.OnlineExamStatus
import com.smart.resources.schools_app.features.users.data.UserAccount
import org.threeten.bp.Duration
import org.threeten.bp.LocalDateTime

/**
 * database can be improved slightly by converting relationship into many to many
 * however it is too rare that multiple students with a teacher in the same class using same device!!
 */
@Entity(
    tableName = LocalOnlineExam.TABLE_NAME,
    // TODO: add re add foreign keys
//    foreignKeys = [
//        ForeignKey(
//            entity = UserAccount::class,
//            parentColumns = ["uid"],
//            childColumns = ["userId"],
//            onDelete = ForeignKey.CASCADE,
//            onUpdate = ForeignKey.CASCADE,
//        ),
//    ],
//    indices = [
//        Index(value = ["userId"])
//    ]
)
data class LocalOnlineExam(
    @PrimaryKey
    val onlineExamId: String,
    val userId: String,
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