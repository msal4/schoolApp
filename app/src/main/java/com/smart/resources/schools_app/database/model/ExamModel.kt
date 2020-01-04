package com.smart.resources.schools_app.database.model

import com.google.gson.annotations.SerializedName
import org.threeten.bp.LocalDateTime

class ExamModel {
    @SerializedName("id_exam")
    val idExam = 0
    @SerializedName("subject_name")
    val subjectName: String? = null
    val date: LocalDateTime? = null
    val note: String? = null
    val type: String? = null
    @SerializedName("class_id")
    val classId = 0
    @SerializedName("class_name")
    val className: String? = null
    @SerializedName("teacher_id")
    val teacherId = 0

}