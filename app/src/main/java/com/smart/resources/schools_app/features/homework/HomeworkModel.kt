package com.smart.resources.schools_app.features.homework

import com.smart.resources.schools_app.core.extentions.isImage
import com.smart.resources.schools_app.features.homeworkSolution.data.model.HomeworkSolutionModel
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import java.io.File


abstract class BaseHomeworkModel(
    var assignmentName: String,
    var date: LocalDate?,
    var note: String,
    var subjectName: String
){

    override fun toString(): String {
        return "BaseHomeworkModel(assignmentName='$assignmentName', date=$date, note='$note', subjectName='$subjectName')"
    }
}

data class HomeworkModel(
    val idHomework: String,
    val assignmentName: String,
    val date: LocalDate,
    val note: String,
    val attachment: String,
    val subjectName: String,
    val solution: HomeworkSolutionModel?
){
    val hasImage get() = attachment.isImage()
    val hasNote  get() = !note.isBlank()

}

class PostHomeworkModel(
    var attachment: File?= null,
    var classId:String= "",
    assignmentName: String= "",
    date: LocalDate?= null,
    note: String= "",
    subjectName: String= ""
): BaseHomeworkModel(assignmentName, date, note, subjectName){

    override fun toString(): String {
        return "${super.toString()}\n PostHomeworkModel(attachment=$attachment, classId='$classId')"
    }
}

