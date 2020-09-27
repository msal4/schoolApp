package com.smart.resources.schools_app.core.typeConverters.room

import androidx.room.TypeConverter
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneOffset

class LocalDateTimeToTimestampConverter {
    companion object {
        private val ZONE_OFFSET = ZoneOffset.UTC
    }

    @TypeConverter
    fun fromTimestamp(value: Long?): LocalDateTime? {
        if (value != null) {
            return LocalDateTime.ofEpochSecond(value, 0, ZONE_OFFSET)
        }
        return null
    }

    @TypeConverter
    fun toTimestamp(date: LocalDateTime?): Long? {
        if (date != null) {
            return date.toEpochSecond(ZONE_OFFSET)
        }
        return null
    }
}