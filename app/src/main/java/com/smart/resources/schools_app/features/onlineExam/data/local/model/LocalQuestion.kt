package com.smart.resources.schools_app.features.onlineExam.data.local.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.smart.resources.schools_app.core.typeConverters.room.QuestionType

@Entity(
    tableName = LocalQuestion.TABLE_NAME,
    foreignKeys = [
        ForeignKey(
            entity = LocalOnlineExam::class,
            parentColumns = ["onlineExamId"],
            childColumns = ["onlineExamId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE,
        ),
    ],
    indices = [
        Index(value = ["onlineExamId"]),
    ],
)
data class LocalQuestion(
    @PrimaryKey
    val questionId: String,
    val onlineExamId: String,
    val questionText: String,
    val questionType: QuestionType,
    val options: List<String>?,
) {
    companion object {
        const val TABLE_NAME = "Questions"
    }
}