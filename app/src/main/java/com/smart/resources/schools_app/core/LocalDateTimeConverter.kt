package com.smart.resources.schools_app.core

import com.google.gson.*
import com.smart.resources.schools_app.core.util.dateTimeBackendFormatter
import org.threeten.bp.LocalDateTime
import java.lang.reflect.Type


class LocalDateTimeConverter : JsonDeserializer<LocalDateTime>, JsonSerializer<LocalDateTime> {
    override fun deserialize(json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext?): LocalDateTime =
             LocalDateTime.parse(json.asString, dateTimeBackendFormatter)
    override fun serialize(src: LocalDateTime, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement = JsonPrimitive(src.format(dateTimeBackendFormatter))

}