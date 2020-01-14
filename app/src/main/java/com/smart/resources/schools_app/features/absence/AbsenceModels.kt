package com.smart.resources.schools_app.features.absence

import org.threeten.bp.LocalDateTime

open class StudentAbsenceModel(
    val subjectName: String,
    val absenceDate: LocalDateTime)

class AddAbsenceModel(
    val idStudent: String,
    var isChecked: Boolean= false,
    name: String,
    absenceDate: LocalDateTime= LocalDateTime.now()

): StudentAbsenceModel(name, absenceDate)


data class PostAbsenceModel(
    var classId: String= "",
    var subjectName: String= "",
    var studentsId: List<String> = listOf()
)