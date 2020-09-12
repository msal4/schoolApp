package com.smart.resources.schools_app.features.homeworkSolution.domain.model

import com.google.gson.annotations.SerializedName
import org.threeten.bp.LocalDateTime


// TODO: fix this model
data class HomeworkSolutionModel(
    val id: String?,
    val studentName: String?,
    @SerializedName("solutionText")
    val solution: String?,
    @SerializedName("solutionImage")
    val imageUrl: String?,
    val date: LocalDateTime?
) {
    val hasAnswer: Boolean get() = !solution.isNullOrEmpty()
    val hasImage: Boolean get() = imageUrl != null
}