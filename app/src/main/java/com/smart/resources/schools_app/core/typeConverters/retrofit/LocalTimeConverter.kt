package com.smart.resources.schools_app.core.typeConverters.retrofit

import com.google.gson.*
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime
import java.lang.Exception
import java.lang.reflect.Type


class LocalTimeConverter : JsonDeserializer<LocalTime>, JsonSerializer<LocalTime> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): LocalTime = try {
            LocalTime.parse(json.asString)
    } catch (e: Exception) {
        LocalTime.now()
    }

    override fun serialize(
        src: LocalTime,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement = JsonPrimitive(src.format(dateTimeBackendSendFormatter))

}