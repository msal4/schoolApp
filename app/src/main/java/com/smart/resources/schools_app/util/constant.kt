package com.smart.resources.schools_app.util

import org.threeten.bp.format.DateTimeFormatter
import java.util.*
import java.util.logging.Formatter

val dateDisFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale("ar"))
val dateTimeBackendFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)