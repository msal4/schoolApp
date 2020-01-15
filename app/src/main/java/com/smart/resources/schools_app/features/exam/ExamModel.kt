package com.smart.resources.schools_app.features.exam

import com.smart.resources.schools_app.features.homework.HomeworkModel
import org.threeten.bp.LocalDateTime

abstract class Exam (
    var subjectName: String,
    var date: LocalDateTime?,
    var note: String,
    var type: String
)

class ExamModel(
    var idExam:Int?,
    var mark: Int?,
    subjectName: String,
    date: LocalDateTime,
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
    date: LocalDateTime?=null,
    note: String= "",
    examType: String= ""
): Exam(subjectName, date, note, examType)
