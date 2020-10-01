package com.smart.resources.schools_app.core.extentions

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.BidiFormatter
import android.util.Base64
import androidx.lifecycle.MutableLiveData
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.nio.charset.StandardCharsets


fun String.decodeTokenBody(): String {
    val splitString = this.split('.')
//    val base64EncodedHeader = splitString[0]
    val base64EncodedBody = splitString[1]
//    val base64EncodedSignature = splitString[2]
    return base64EncodedBody.base64Decode()
}

private fun String.base64Decode(): String {
    val bytes: ByteArray = Base64.decode(this, Base64.URL_SAFE)
    return String(bytes, StandardCharsets.UTF_8)
}

fun String.openUrl(context: Context) {
    val url =
        if (!startsWith("http://") && !startsWith("https://"))
            "http://$this"
        else this

    val uri = Uri.parse(url)
    val intent = Intent(Intent.ACTION_VIEW, uri)
    context.startActivity(intent)
}

fun String?.isImage() = (this != null && (this.toLowerCase().endsWith(".jpg") || this.toLowerCase()
    .endsWith(".jpeg") || this.toLowerCase().endsWith(".png")))

fun String?.isPdf(): Boolean = this?.toLowerCase()?.endsWith(".pdf") ?: false


fun String.replaceArNumbersWithEn() = this
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


fun String.asRequestBody(): RequestBody {
    return RequestBody.create(MediaType.parse("text/plain"), this.trim())
}

fun File.asBodyPart(fieldName: String): MultipartBody.Part? {
    val requestBody= RequestBody.create(MediaType.parse("Image/*"), this)
    return MultipartBody.Part.createFormData(fieldName, name, requestBody)
}

fun <T> MutableLiveData<T>.notifyObservers() {
    this.value = this.value
}

/// fixes bidirectional text
fun String.toUnicodeString(): String = BidiFormatter.getInstance().unicodeWrap(this)

fun String?.isNotNullOrBlank ()= !isNullOrBlank()
fun List<Any>?.isNotNullOrEmpty ()= !isNullOrEmpty()

/**
 * validate an integer to be:
 *
 * 1- not null
 *
 * 2- more than > -1
 *
 * 3- less than < max
 *
 */
fun Int?.isValidIndex (max:Int= Integer.MAX_VALUE)= this!=null && this > -1 && this < max