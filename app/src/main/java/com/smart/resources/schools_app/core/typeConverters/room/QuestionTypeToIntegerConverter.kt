package com.smart.resources.schools_app.core.typeConverters.room

import androidx.room.TypeConverter

enum class QuestionType(val value: Int) {
    MULTI_CHOICE(1),
    CORRECTNESS(2),
    NORMAL(3);

    companion object {
        fun fromValue(value: Int): QuestionType {
            return values().find { it.value== value }?:NORMAL
        }
    }
}

class QuestionTypeToIntegerConverter {
    @TypeConverter
    fun fromInteger(value: Int?): QuestionType? {
        if (value != null) {
            return QuestionType.fromValue(value)
        }
        return null
    }

    @TypeConverter
    fun toInteger(questionType: QuestionType?): Int? {
        return questionType?.value
    }
}