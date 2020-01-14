package com.smart.resources.schools_app.features.advertising

import com.smart.resources.schools_app.core.utils.isImage
import org.threeten.bp.LocalDateTime
import java.io.Serializable

data class AdvertisingModel(
    val advertisementId: String,
    val title: String,
    val attachment: String,
    val note: String,
    val date: LocalDateTime
) {
    val hasImage get() = attachment.isImage()
}