package com.smart.resources.schools_app.features.homeworkAnswer.getHomeworkAnswers

import org.threeten.bp.LocalDateTime


data class HomeworkAnswerModel(
    val id: String?,
    val studentName: String?,
    val answer: String?,
    val attachmentUrl: String?,
    val date: LocalDateTime
) {
    val hasAnswer: Boolean get() = !answer.isNullOrEmpty()
    val hasImage: Boolean get() = attachmentUrl != null
}