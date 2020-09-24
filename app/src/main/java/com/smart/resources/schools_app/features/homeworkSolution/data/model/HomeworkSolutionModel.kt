package com.smart.resources.schools_app.features.homeworkSolution.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.smart.resources.schools_app.core.extentions.isImage
import kotlinx.android.parcel.Parcelize
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime

// TODO: fix this model
@Parcelize
data class HomeworkSolutionModel(
    @SerializedName("idSolution")
    val id: String?,
    val homeworkId: String?,
    val studentName: String?,
    @SerializedName("solutionText")
    val solution: String?,
    @SerializedName("solutionImage")
    val imageUrl: String?,
    @SerializedName("solutionDate")
    val date: LocalDate?
) : Parcelable {
    val hasAnswer: Boolean get() = !solution.isNullOrEmpty() && solution.toLowerCase() != "none".toLowerCase()
    val hasImage: Boolean get() = imageUrl != null && imageUrl.isImage()
}