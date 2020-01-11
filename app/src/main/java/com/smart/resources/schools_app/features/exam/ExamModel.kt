package com.smart.resources.schools_app.features.exam

import org.threeten.bp.LocalDateTime

abstract class Exam (
    var subjectName: String,
    var date: LocalDateTime?,
    var note: String,
    var type: String
)

class ExamModel(
    var mark: Int?,
    subjectName: String,
    date: LocalDateTime,
    note: String,
    type: String
) : Exam(subjectName, date, note, type)


class PostExamModel(
    var classId:String= "",
    subjectName: String= "",
    date: LocalDateTime?=null,
    note: String= "",
    examType: String= ""
): Exam(subjectName, date, note, examType)
