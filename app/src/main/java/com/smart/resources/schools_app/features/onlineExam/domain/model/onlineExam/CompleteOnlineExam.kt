package com.smart.resources.schools_app.features.onlineExam.domain.model.onlineExam

import com.smart.resources.schools_app.features.onlineExam.domain.model.Question

data class CompleteOnlineExam(
    val addOnlineExam: AddOnlineExam,
    val questions:List<Question>
)


