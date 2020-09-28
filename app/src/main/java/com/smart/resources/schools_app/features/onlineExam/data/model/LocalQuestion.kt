package com.smart.resources.schools_app.features.onlineExam.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

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
    Index(value = ["onlineExamId"])
    ]
)
data class LocalQuestion(
    @PrimaryKey
    val questionId: String,
    val onlineExamId: String,
    val questionText: String,
    val questionType: Int,
    val options: List<Int>?,
) {
    companion object {
        const val TABLE_NAME = "Questions"
    }
}