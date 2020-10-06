package com.smart.resources.schools_app.core.typeConverters.room

import androidx.room.TypeConverter

enum class OnlineExamStatus(val value: Int) {
    INACTIVE(0),
    ACTIVE(1),
    COMPLETED(2),
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
            return examStatus?.ordinal
    }
}