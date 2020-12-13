package com.smart.resources.schools_app.features.fees

import org.threeten.bp.LocalDate

data class FeesDetail (
    val idStudentFees: Int,

    val feesId: Int,

    val studentId: Int,

    val date: LocalDate,
    val pay: String,
    val note: String,
    val idFees: Int,
    val feeAmount: Int,

    val attempt: Int,
)