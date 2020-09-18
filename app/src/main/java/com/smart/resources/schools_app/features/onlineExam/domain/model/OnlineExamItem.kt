package com.smart.resources.schools_app.features.onlineExam.domain.model

import org.threeten.bp.Duration
import org.threeten.bp.LocalDateTime

data class OnlineExamItem (
    val id:String,
    val subjectName:String,
    val date:LocalDateTime,
    val duration:Duration,
    val numberOfQuestions:Int,
    val examStatus: OnlineExamStatus,
){
    val isLocked get() = examStatus== OnlineExamStatus.LOCKED
}

enum class OnlineExamStatus{
    LOCKED,
    READY,
    IN_PROGRESS,
    COMPLETED
}