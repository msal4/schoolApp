package com.smart.resources.schools_app.core.extentions

import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.hadiyarajesh.flower.ApiEmptyResponse
import com.hadiyarajesh.flower.ApiErrorResponse
import com.hadiyarajesh.flower.ApiResponse
import com.hadiyarajesh.flower.ApiSuccessResponse
import com.haytham.coder.extensions.toColor
import com.smart.resources.schools_app.R
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.threeten.bp.Duration
import java.io.File

fun String?.isImage() = (this != null && (this.toLowerCase().endsWith(".jpg") || this.toLowerCase()
    .endsWith(".jpeg") || this.toLowerCase().endsWith(".png")))

fun String?.isPdf(): Boolean = this?.toLowerCase()?.endsWith(".pdf") ?: false


fun String.asRequestBody(): RequestBody {
    return RequestBody.create(MediaType.parse("text/plain"), this.trim())
}

fun File.asBodyPart(fieldName: String): MultipartBody.Part? {
    val requestBody = RequestBody.create(MediaType.parse("Image/*"), this)
    return MultipartBody.Part.createFormData(fieldName, name, requestBody)
}

fun <T> MutableLiveData<T>.notifyObservers() {
    this.value = this.value
}

fun ViewGroup.showSnackBar(msg: String, isError: Boolean = true) {
    Snackbar.make(this, msg, Snackbar.LENGTH_LONG).apply {
        val color = if (isError) Color.RED else R.color.light_green_a700
        setTextColor(color)
        setBackgroundTint(R.color.snackbar_background.toColor(context))
        layoutDirection= View.LAYOUT_DIRECTION_RTL
        view.layoutDirection = View.LAYOUT_DIRECTION_RTL
        show()
    }
}

fun RecyclerView.createGridLayout(adapter: RecyclerView.Adapter<out RecyclerView.ViewHolder>) {
    val itemMargin = resources.getDimension(R.dimen.item_margin).toInt()
    setPadding(itemMargin, itemMargin, 0, itemMargin)
    layoutManager = GridLayoutManager(context, 2)
    this.adapter = adapter
}

fun TextView.applyGradient(@ColorInt vararg colors: Int) {
    measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
    val textShader: Shader = LinearGradient(
        x + measuredWidth,
        0f,
        x,
        textSize,
        colors,
        null,
        Shader.TileMode.CLAMP
    )
    paint.shader = textShader
}

fun Long.toDuration(): Duration = Duration.ofMillis(this)

fun <T : Any> Collection<T>.filterIndexes(indexFilter: List<Int>): Collection<T> =
    filterIndexed { index, _ ->
        indexFilter.contains(index)
    }


fun <T, R> ApiResponse<T>.withNewData(mapper: (T) -> R): ApiResponse<R> {
    return when (this) {
        is ApiEmptyResponse -> ApiEmptyResponse()
        is ApiSuccessResponse -> {
            if (body != null) {
                ApiSuccessResponse(
                    body = mapper(body!!),
                    headers = headers,
                )
            } else {
                ApiEmptyResponse()
            }
        }
        is ApiErrorResponse -> ApiErrorResponse(
            errorMessage = errorMessage,
            statusCode = statusCode,
        )
    }
}

val <T>ApiErrorResponse<T>.combinedMessage:String get() = "Status Code:$statusCode\n $errorMessage"

internal val Any.TAG: String
    get() {
        return if (!javaClass.isAnonymousClass) {
            val name = javaClass.simpleName
            // first 23 chars
            if (name.length <= 23) name else name.substring(0, 23)
        } else {
            val name = javaClass.name
            // last 23 chars
            if (name.length <= 23) name else name.substring(name.length - 23, name.length)
        }
    }