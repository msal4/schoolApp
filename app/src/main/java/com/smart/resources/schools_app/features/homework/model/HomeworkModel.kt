package com.smart.resources.schools_app.features.homework.model

import org.threeten.bp.LocalDateTime
import java.io.File


abstract class BaseHomeworkModel(
    var assignmentName: String,
    var date: LocalDateTime?,
    var note: String,
    var subjectName: String
){

    override fun toString(): String {
        return "BaseHomeworkModel(assignmentName='$assignmentName', date=$date, note='$note', subjectName='$subjectName')"
    }
}

class HomeworkModel(
    val idHomework: Int,
    assignmentName: String,
    date: LocalDateTime,
    note: String,
    val attachment: String,
    subjectName: String

): BaseHomeworkModel(assignmentName, date, note, subjectName)

class PostHomeworkModel(
    var attachment: File?= null,
    var classId:String= "",
    assignmentName: String= "",
    date: LocalDateTime?= null,
    note: String= "",
    subjectName: String= ""
): BaseHomeworkModel(assignmentName, date, note, subjectName){

    override fun toString(): String {
        return "${super.toString()}\n PostHomeworkModel(attachment=$attachment, classId='$classId')"
    }
}

