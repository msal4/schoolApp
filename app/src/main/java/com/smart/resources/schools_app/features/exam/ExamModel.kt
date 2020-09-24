package com.smart.resources.schools_app.features.exam

import org.threeten.bp.LocalDate

abstract class Exam (
    var subjectName: String,
    var date: LocalDate?,
    var note: String,
    var type: String
)

class ExamModel(
    var idExam:Int?,
    var mark: Int?,
    subjectName: String,
    date: LocalDate,
    note: String,
    type: String
) : Exam(subjectName, date, note, type){

    fun isContentSame(model: ExamModel) =
        subjectName== model.subjectName &&
                date== model.date &&
                note== model.note &&
                type == model.type &&
                mark== model.mark
}


class PostExamModel(
    var classId:String= "",
    subjectName: String= "",
    date: LocalDate?=null,
    note: String= "",
    examType: String= ""
): Exam(subjectName, date, note, examType)
