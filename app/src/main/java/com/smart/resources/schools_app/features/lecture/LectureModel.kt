package com.smart.resources.schools_app.features.lecture

import com.google.gson.annotations.SerializedName
import com.smart.resources.schools_app.features.homework.HomeworkModel
import org.threeten.bp.LocalDateTime

data class LectureModel (
    @SerializedName("idOnline")
    var id: String,
    @SerializedName("subject")
    var title: String?,
    @SerializedName("lecture")
    var subtitle: String?,
    var date: LocalDateTime?,
    var url: String?
)
