package com.smart.resources.schools_app.adapter

import com.google.gson.*
import com.smart.resources.schools_app.util.dateTimeBackendFormatter
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import java.lang.reflect.Type
import java.util.*



class LocalDateTimeConverter : JsonDeserializer<LocalDateTime>, JsonSerializer<LocalDateTime> {
    override fun deserialize(json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext?): LocalDateTime =
             LocalDateTime.parse(json.asString, dateTimeBackendFormatter)
    override fun serialize(src: LocalDateTime, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement = JsonPrimitive(src.format(dateTimeBackendFormatter))

}