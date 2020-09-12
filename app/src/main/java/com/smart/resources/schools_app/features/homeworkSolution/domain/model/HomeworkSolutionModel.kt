package com.smart.resources.schools_app.features.homeworkSolution.domain.model

import org.threeten.bp.LocalDateTime


data class HomeworkSolutionModel(
    val id: String?,
    val studentName: String?,
    val answer: String?,
    val attachmentUrl: String?,
    val date: LocalDateTime
) {
    val hasAnswer: Boolean get() = !answer.isNullOrEmpty()
    val hasImage: Boolean get() = attachmentUrl != null
}