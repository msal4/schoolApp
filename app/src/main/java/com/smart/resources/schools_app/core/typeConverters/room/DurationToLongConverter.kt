package com.smart.resources.schools_app.core.typeConverters.room

import androidx.room.TypeConverter
import org.threeten.bp.Duration

class DurationToLongConverter {
    @TypeConverter
    fun fromLong(value: Long?): Duration? {
        if (value != null) {
            return Duration.ofMillis(value)
        }
        return null
    }

    @TypeConverter
    fun toLong(duration: Duration?): Long? {
        if (duration != null) {
            return duration.toMillis()
        }
        return null
    }
}