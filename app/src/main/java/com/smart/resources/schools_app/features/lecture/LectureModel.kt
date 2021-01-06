package com.smart.resources.schools_app.features.lecture

import com.google.gson.annotations.SerializedName
import org.threeten.bp.LocalDate


data class LectureModel (
    val idOnline: Long,
    val subjectID: Long,
    val url: String,
    @SerializedName("lecture")
    val subtitle: String,
    @SerializedName("pdfUrl")
    var pdfURL: String,
    val date: LocalDate,
    val idSubject: Long,
    val subjectName: String,
    val className: String,
    val schoolID: Long
)