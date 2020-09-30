package com.smart.resources.schools_app.features.absence

import org.threeten.bp.LocalDate

open class StudentAbsenceModel(
    val subjectName: String,
    val absenceDate: LocalDate,
)

class AddAbsenceModel(
    val idStudent: String,
    var isChecked: Boolean = false,
    name: String,
    absenceDate: LocalDate = LocalDate.now()

) : StudentAbsenceModel(name, absenceDate)


data class PostAbsenceModel(
    var classId: String = "",
    var subjectName: String = "",
    var studentsId: List<String> = listOf()
)