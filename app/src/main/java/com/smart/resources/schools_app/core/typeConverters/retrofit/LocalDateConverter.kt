package com.smart.resources.schools_app.core.typeConverters.retrofit

import com.google.gson.*
import org.threeten.bp.LocalDate
import java.lang.Exception
import java.lang.reflect.Type


class LocalDateConverter : JsonDeserializer<LocalDate>, JsonSerializer<LocalDate> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): LocalDate = try {
        if (json.asString.contains('T'))
            LocalDate.parse(json.asString, dateTimeBackendFormatter)
        else
            LocalDate.parse(json.asString, dateTimeBackendSendFormatter)

    } catch (e: Exception) {
        LocalDate.now()
    }

    override fun serialize(
        src: LocalDate,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement = JsonPrimitive(src.format(dateTimeBackendSendFormatter))

}