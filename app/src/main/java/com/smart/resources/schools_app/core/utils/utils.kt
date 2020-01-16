package com.smart.resources.schools_app.core.utils

import android.content.res.Resources
import android.util.Base64
import android.widget.EditText
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


/**
 * this method completes validatePhone AND MUST BE CALLED AFTER IT TO ENSURE CORRECT INPUT
 * it gets a valid phone number as entered by the user
 * and convert it to the complete
 * which is 964##########
 * either
 * by deleting "00" or "+"
 * or by inserting 964
 * or keeping it in its original form
 * @param rawPhoneNo the phone number editable object as entered by the user
 * @return complete phone number
 */
fun String.getFormattedPhone(): String {
    return when (this.length) {
        10 -> "+964$this"
        11 -> "+964" + this.substring(1)
        13 -> "+$this"
        14 -> this
        15 -> "+${this.substring(2)}"
        else -> throw NumberFormatException()
    }
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

fun String?.isImage()= (this != null && (this.toLowerCase().endsWith(".jpg")||this.toLowerCase().endsWith(".png")))


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