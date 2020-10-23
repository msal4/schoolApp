package com.smart.resources.schools_app.core.typeConverters.retrofit

import com.google.gson.*
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.LocalTime
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.DecimalStyle
import org.threeten.bp.format.FormatStyle
import java.lang.reflect.Type
import java.util.*

// TODO: move with hilt di
val locale: Locale = Locale.getDefault()// TODO: fix this

val timeDisFormatter: DateTimeFormatter =
    DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)
        .withDecimalStyle(DecimalStyle.of(locale))

val dateDisFormatter: DateTimeFormatter =
    DateTimeFormatter
        .ofLocalizedDate(FormatStyle.SHORT)
        .withDecimalStyle(DecimalStyle.of(locale)) //"""d\MM\yyyy""",

val dateTimeDisFormatter: DateTimeFormatter =
    DateTimeFormatter
        .ofLocalizedDateTime(FormatStyle.SHORT)
        .withDecimalStyle(DecimalStyle.of(locale))

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