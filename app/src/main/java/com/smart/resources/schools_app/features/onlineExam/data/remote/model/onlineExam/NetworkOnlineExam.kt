package com.smart.resources.schools_app.features.onlineExam.data.remote.model.onlineExam

import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime

class NetworkOnlineExam(
    val idOnlineExam: Long?,
    val teacherId: Long?,
    val availableQuestions: Int?,
    override val subjectName: String?,
    override val date: LocalDate?,
    override val time: LocalTime?,
    override val examTime: Long?,
    override val nuOfRequiredQuestion: Int?,
    override val examKey: String?,
    override val isFinished: Int?,
    override val status: Int?
):BaseNetworkOnlineExam