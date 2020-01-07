package com.smart.resources.schools_app.database.model
import org.threeten.bp.LocalDateTime

data class ExamModel (
    val subjectName: String,
    val date: LocalDateTime,
    val note: String,
    val type: String,
    val mark: Int?
)