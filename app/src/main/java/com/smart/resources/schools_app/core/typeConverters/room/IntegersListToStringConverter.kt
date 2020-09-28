package com.smart.resources.schools_app.core.typeConverters.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class IntegersListToStringConverter {
    @TypeConverter
    fun fromString(value: String?): List<Int>? {
        if(value !=null) {
            val listType: Type = object : TypeToken<List<Int>>() {}.type
            return Gson().fromJson(value, listType)
        }
        return null
    }

    @TypeConverter
    fun toString(list: List<Int>?): String? {
        if(list == null) return null
        val gson = Gson()
        return gson.toJson(list)
    }
}
