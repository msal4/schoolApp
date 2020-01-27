package com.smart.resources.schools_app.features.advertising

import com.smart.resources.schools_app.core.extentions.isImage
import org.threeten.bp.LocalDateTime

data class AdvertisingModel(
    val advertisementId: String,
    val title: String,
    val attachment: String,
    val note: String,
    val date: LocalDateTime
) {
    val hasImage get() = attachment.isImage()
}