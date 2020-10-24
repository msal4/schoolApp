package com.smart.resources.schools_app.features.onlineExam.data.local.model

import androidx.room.Embedded
import androidx.room.Relation

data class QuestionWithAnswer(
    @Embedded val localQuestion: LocalQuestion,
//    @Relation(
//        parentColumn = "questionId",
//        entityColumn = "questionId"
//    )
    @Embedded(prefix = "answers_") val localAnswer: LocalAnswer?,
)