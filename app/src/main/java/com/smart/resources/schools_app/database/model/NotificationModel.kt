package com.smart.resources.schools_app.database.model

import org.threeten.bp.LocalDateTime

 class NotificationModel(
    val content: String,
    val title: String,
    val date: LocalDateTime,
    val category: String?= null
){
    val categoryExist get() = !category.isNullOrEmpty()
}
