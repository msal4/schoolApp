package com.smart.resources.schools_app.core.typeConverters.room

import androidx.room.TypeConverter

// ui list is sorted according to enum order
enum class OnlineExamStatus(val value: Int) {
    ACTIVE(1),
    INACTIVE(0),
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