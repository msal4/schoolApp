package com.smart.resources.schools_app.core.typeConverters.retrofit

import com.google.gson.*
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.LocalTime
import org.threeten.bp.format.DateTimeFormatter
import java.lang.Exception
import java.lang.reflect.Type
import java.util.*


val timeDisFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("""hh:mm a""", Locale("ar"))
val dateDisFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("""d\MM\yyyy""", Locale("ar"))
val dateTimeDisFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("d\\MM\\yyyy\n   HH:mm a", Locale("ar"))
val dateTimeBackendFormatter: DateTimeFormatter =
    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
val dateTimeBackendSendFormatter: DateTimeFormatter =
    DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH)


class LocalDateTimeConverter : JsonDeserializer<LocalDateTime>, JsonSerializer<LocalDateTime> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): LocalDateTime = try {
        if (json.asString.contains('T'))
            LocalDateTime.parse(json.asString, dateTimeBackendFormatter)
        else LocalDateTime.of(
            LocalDate.parse(json.asString, dateTimeBackendSendFormatter),
            LocalTime.now()
        )
    } catch (e: Exception) {
        LocalDateTime.now()
    }

    override fun serialize(
        src: LocalDateTime,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement = JsonPrimitive(src.format(dateTimeBackendSendFormatter))

}