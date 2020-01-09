package com.smart.resources.schools_app.features.exam

import org.threeten.bp.LocalDateTime

abstract class Exam (
    val subjectName: String,
    val date: LocalDateTime,
    val note: String,
    val type: String
)

class ExamModel(
    var mark: Int?, subjectName: String, date: LocalDateTime, note: String, type: String
) : Exam(subjectName, date, note, type)


class ExamPostModel(
    var classId:Int, subjectName: String, date: LocalDateTime, note: String, type: String
): Exam(subjectName, date, note, type)
