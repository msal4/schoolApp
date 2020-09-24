package com.smart.resources.schools_app.features.notification

import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime

 class NotificationModel(
    val content: String,
    val title: String,
    val date: LocalDate,
    val category: String?= null
){
    val categoryExist get() = !category.isNullOrEmpty()
}
