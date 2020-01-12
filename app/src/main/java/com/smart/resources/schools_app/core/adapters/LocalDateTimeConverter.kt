package com.smart.resources.schools_app.core.adapters

import com.google.gson.*
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.LocalTime
import org.threeten.bp.format.DateTimeFormatter
import java.lang.reflect.Type
import java.util.*


val dateDisFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("""d\MM\yyyy""", Locale("ar"))
val dateTimeBackendFormatter: DateTimeFormatter =
    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
val dateTimeBackendSendFormatter: DateTimeFormatter =
    DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH)


class LocalDateTimeConverter : JsonDeserializer<LocalDateTime>, JsonSerializer<LocalDateTime> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ):
            LocalDateTime = if (json.asString.contains('T'))
        LocalDateTime.parse(json.asString, dateTimeBackendFormatter)
    else LocalDateTime.of(
        LocalDate.parse(json.asString, dateTimeBackendSendFormatter),
        LocalTime.now()
    )

    override fun serialize(
        src: LocalDateTime,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement = JsonPrimitive(src.format(dateTimeBackendSendFormatter))

}