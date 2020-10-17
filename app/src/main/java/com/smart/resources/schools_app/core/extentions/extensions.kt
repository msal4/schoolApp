package com.smart.resources.schools_app.core.extentions

import android.content.Context
import android.content.pm.ApplicationInfo
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Rect
import android.graphics.Shader
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.hadiyarajesh.flower.ApiEmptyResponse
import com.hadiyarajesh.flower.ApiErrorResponse
import com.hadiyarajesh.flower.ApiResponse
import com.hadiyarajesh.flower.ApiSuccessResponse
import com.haytham.coder.extensions.screenSize
import com.haytham.coder.extensions.toColor
import com.smart.resources.schools_app.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.threeten.bp.Duration
import java.io.File

fun String?.isImage() = (this != null && (this.toLowerCase().endsWith(".jpg") || this.toLowerCase()
    .endsWith(".jpeg") || this.toLowerCase().endsWith(".png")))

fun String?.isPdf(): Boolean = this?.toLowerCase()?.endsWith(".pdf") ?: false


fun String.asRequestBody(): RequestBody {
    return this.trim().toRequestBody("text/plain".toMediaTypeOrNull())
}

fun File.asBodyPart(fieldName: String): MultipartBody.Part? {
    val requestBody = this.asRequestBody("Image/*".toMediaTypeOrNull())
    return MultipartBody.Part.createFormData(fieldName, name, requestBody)
}

fun <T> MutableLiveData<T>.notifyObservers() {
    this.value = this.value
}

fun ViewGroup.showSnackBar(msg: String, isError: Boolean = true) {
    Snackbar.make(this, msg, Snackbar.LENGTH_LONG).apply {
        val color = if (isError) Color.RED else R.color.light_green_a700.toColor(context)
        setTextColor(color)
        setBackgroundTint(R.color.snackbar_background.toColor(context))
        layoutDirection = View.LAYOUT_DIRECTION_RTL
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

val <T>ApiErrorResponse<T>.combinedMessage: String get() = "Status Code:$statusCode\n $errorMessage"

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

fun <T> debounce(
    waitMs: Long = 300L,
    coroutineScope: CoroutineScope,
    destinationFunction: (T) -> Unit
): (T) -> Unit {
    var debounceJob: Job? = null
    return { param: T ->
        debounceJob?.cancel()
        debounceJob = coroutineScope.launch {
            delay(waitMs)
            destinationFunction(param)
        }
    }
}

val View.windowHeight
    get():Int {
        val windowRect = Rect()
        getWindowVisibleDisplayFrame(windowRect)
        return context.screenSize.y - windowRect.top
    }

fun Fragment.showConfirmationDialog(
    @StringRes message: Int,
    dismissAction: (() -> Unit)? = null,
    okAction: () -> Unit,
) {
    MaterialAlertDialogBuilder(requireContext())
        .setTitle(R.string.confirmation)
        .setMessage(message)
        .setNegativeButton(resources.getString(R.string.cancel)) { _, _ ->
//            cancelAction?.invoke()
        }
        .setPositiveButton(resources.getString(R.string.ok)) { _, _ ->
            okAction()
        }.create().apply {
            window?.decorView?.layoutDirection = View.LAYOUT_DIRECTION_RTL
            setOnDismissListener {
                dismissAction?.invoke()
            }
        }
        .show()
}

/**
 * observe for first not null, not empty value
 */
fun <T> LiveData<T>.observeOnce(owner: LifecycleOwner, observer: (T) -> Unit) {
    observe(
        owner,
        object : Observer<T> {
            override fun onChanged(t: T) {
                if (t != null) {
                    if (t is Collection<*> && t.isEmpty()) return

                    observer(t)
                    removeObserver(this)
                }
            }
        },
    )
}

/**
 * where app in debug or release mode
 */
val Context.isAppInDebugMode get() = applicationInfo.flags != 0 and ApplicationInfo.FLAG_DEBUGGABLE

fun View.hideKeyboard() {
    val imm: InputMethodManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(this.windowToken, 0)
}