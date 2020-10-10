package com.smart.resources.schools_app.core.typeConverters.room

import androidx.room.TypeConverter

// ui list is sorted according to enum order
enum class OnlineExamStatus(val value: Int) {
    ACTIVE(1),
    INACTIVE(0),
    COMPLETED(2);

    companion object{
        fun fromValue(value:Int):OnlineExamStatus{
           return  OnlineExamStatus.values().find { it.value == value }?:INACTIVE
        }
    }
}

class OnlineExamStatusToIntegerConverter {
    @TypeConverter
    fun fromInteger(value: Int?): OnlineExamStatus? {
        if (value != null) {
            return OnlineExamStatus.fromValue(value)
        }
        return null
    }

    @TypeConverter
    fun toInteger(examStatus: OnlineExamStatus?): Int? {
            return examStatus?.value
    }
}