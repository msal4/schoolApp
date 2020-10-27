package com.smart.resources.schools_app.core.extentions

import java.lang.Exception
import java.util.regex.Matcher
import java.util.regex.Pattern

private val youTubeUrlRegEx = "^(https?)?(://)?(www.)?(m.)?((youtube.com)|(youtu.be))/"
private val videoIdRegex = arrayOf(
    "\\?vi?=([^&]*)",
    "watch\\?.*v=([^&]*)",
    "(?:embed|vi?)/([^/?]*)",
    "^([A-Za-z0-9\\-]*)"
)

fun String?.extractVideoIdFromUrl(): String? {
    if(this == null) return null

    val url= this
    val youTubeLinkWithoutProtocolAndDomain = youTubeLinkWithoutProtocolAndDomain(url)
    for (regex in videoIdRegex) {
        val compiledPattern: Pattern = Pattern.compile(regex)
        val matcher: Matcher = compiledPattern.matcher(youTubeLinkWithoutProtocolAndDomain)
        if (matcher.find()) {
            return matcher.group(1)
        }
    }
    return null
}

/**
 * extract start time from youtube URL
 * default to 0 if start time not found
 */
fun String?.extractStartTimeFromUrl(): Int {
    if(this == null) return 0

    val url= this
    val splittedUrl= url.split("?t=")
    if(splittedUrl.size > 1){
        val startTimeString= splittedUrl.last()
        return try {
            return startTimeString.toInt()
        }catch (e:Exception){
            0
        }
    }
    return 0
}

private fun youTubeLinkWithoutProtocolAndDomain(url: String): String {
    val compiledPattern: Pattern = Pattern.compile(youTubeUrlRegEx)
    val matcher: Matcher = compiledPattern.matcher(url)
    return if (matcher.find()) {
        url.replace(matcher.group(), "")
    } else url
}