package com.smart.resources.schools_app.core.util

import android.content.res.Resources
import android.util.Base64
import java.nio.charset.StandardCharsets


fun pxToDp(px: Int): Int {
    return (px / Resources.getSystem().displayMetrics.density).toInt()
}

fun dpToPx(dp: Int): Int {
    return (dp * Resources.getSystem().displayMetrics.density).toInt()
}
fun decodeToken(jwtToken: String): String {
    val splitString = jwtToken.split('.')
    val base64EncodedHeader = splitString[0]
    val base64EncodedBody = splitString[1]
    val base64EncodedSignature = splitString[2]
    val body = base64Decode(base64EncodedBody)
    return body
}
 private fun base64Decode(encoded: String): String {
    val bytes: ByteArray = Base64.decode(encoded, Base64.URL_SAFE)
    return String(bytes, StandardCharsets.UTF_8)
}

fun String.withEngNums() = this
    .replace('٠', '0')
    .replace('١', '1')
    .replace('٢', '2')
    .replace('٣', '3')
    .replace('٤', '4')
    .replace('٥', '5')
    .replace('٦', '6')
    .replace('٧', '7')
    .replace('٨', '8')
    .replace('٩', '9')