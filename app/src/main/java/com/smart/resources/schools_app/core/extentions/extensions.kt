package com.smart.resources.schools_app.core.extentions

import android.content.res.Resources
import android.util.Base64
import androidx.lifecycle.MutableLiveData
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.nio.charset.StandardCharsets


fun pxToDp(px: Int): Int {
    return (px / Resources.getSystem().displayMetrics.density).toInt()
}

fun dpToPx(dp: Int): Int {

    return (dp * Resources.getSystem().displayMetrics.density).toInt()
}


fun String.decodeToken(): String {
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

fun String?.isImage()= (this != null && (this.toLowerCase().endsWith(".jpg")||this.toLowerCase().endsWith(".jpeg")||this.toLowerCase().endsWith(".png")))
fun String?.isPdf(): Boolean = this?.toLowerCase()?.endsWith(".pdf")?: false


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

fun List<Any>.concatStrings(): String{
    val sb= StringBuilder()
    this.map { it.toString() }.forEach {
        sb.append(it).append(',')
    }

    return sb.substring(0, sb.length-1)
}

fun String.asRequestBody() :RequestBody= RequestBody.create(MediaType.parse("text/plain"),this.trim())

fun File.asBodyPart(fieldName:String) :MultipartBody.Part?{
    val requestBody = RequestBody.create(MediaType.parse("Image/*"), this)
    return MultipartBody.Part.createFormData(fieldName, name, requestBody)
}

fun <T> MutableLiveData<T>.notifyObservers() {
    this.value = this.value
}