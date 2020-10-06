package com.smart.resources.schools_app.features.onlineExam.data.remote.model.onlineExam

import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime

interface BaseNetworkOnlineExam {
    val subjectName: String?
    val date: LocalDate?
    val time: LocalTime?
    val examTime: Long?
    val nuOfRequiredQuestion: Int?
    val examKey: String?
    val isFinished: Int?
    val status: Int?
}