package com.smart.resources.schools_app.core.typeConverters.room

import androidx.room.TypeConverter

enum class QuestionType{
    NORMAL,
    CORRECTNESS,
    MULTI_CHOICE
}
class QuestionTypeToIntegerConverter {
    @TypeConverter
    fun fromInteger(value: Int?): QuestionType? {
        if (value != null) {
            return QuestionType.values()[value]
        }
        return null
    }

    @TypeConverter
    fun toInteger(questionType: QuestionType?): Int? {
            return questionType?.ordinal
    }
}