package com.smart.resources.schools_app.features.homework

import org.threeten.bp.LocalDateTime

data class HomeworkModel(
    val assignmentName: String,
    val attachment: String,
    val date: LocalDateTime,
    val idHomework: Int,
    val note: String,
    val subjectName: String
)