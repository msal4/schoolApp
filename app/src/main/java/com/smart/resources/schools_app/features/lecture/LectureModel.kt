package com.smart.resources.schools_app.features.lecture

import com.google.gson.annotations.SerializedName
import org.threeten.bp.LocalDate

data class LectureModel (
    @SerializedName("idOnline")
    var id: String,
    @SerializedName("subject")
    var title: String?,
    @SerializedName("lecture")
    var subtitle: String?,
    var date: LocalDate?,
    var url: String?
)
