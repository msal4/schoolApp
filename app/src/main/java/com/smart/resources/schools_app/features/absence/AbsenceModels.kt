package com.smart.resources.schools_app.features.absence

import org.threeten.bp.LocalDateTime



class StudentAbsenceModel(
    val subjectName: String,
    val absenceDate: LocalDateTime)

data class PostAbsenceModel(
    val subjectName: String= "",
    val studentsId: MutableList<String> = mutableListOf()
)