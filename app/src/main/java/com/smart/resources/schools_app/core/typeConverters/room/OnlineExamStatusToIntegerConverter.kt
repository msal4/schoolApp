package com.smart.resources.schools_app.core.typeConverters.room

import androidx.room.TypeConverter

enum class OnlineExamStatus {
    LOCKED,
    COMPLETED,
    ACTIVE,
}

class OnlineExamStatusToIntegerConverter {
    @TypeConverter
    fun fromInteger(value: Int?): OnlineExamStatus? {
        if (value != null) {
            return OnlineExamStatus.values()[value]
        }
        return null
    }

    @TypeConverter
    fun toInteger(examStatus: OnlineExamStatus?): Int? {
        if (examStatus != null) {
            return examStatus.ordinal
        }
        return null
    }
}