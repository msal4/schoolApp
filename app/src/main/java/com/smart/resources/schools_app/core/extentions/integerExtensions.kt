package com.smart.resources.schools_app.core.extentions

import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.os.CountDownTimer
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import org.threeten.bp.Duration

fun Int.toColorResource(context: Context) = ContextCompat.getColor(context, this)
fun Int.toColorStateList(context: Context) = ContextCompat.getColorStateList(context, this)

fun Int.toDimen(context: Context) = context.resources.getDimension(this)

fun Int.toDrawableResource(context: Context): Drawable? =
    ResourcesCompat.getDrawable(context.resources, this, null)

fun Int.toStringResource(context: Context) = context.resources.getString(this)

fun pxToDp(px: Int): Int {
    return (px / Resources.getSystem().displayMetrics.density).toInt()
}

fun dpToPx(dp: Int): Int {

    return (dp * Resources.getSystem().displayMetrics.density).toInt()
}


fun Long.toDuration(): Duration = Duration.ofMillis(this)

fun Duration.startCountDown(
    onFinished: (() -> Unit)? = null,
    onTicked: ((remainingDuration: Duration) -> Unit)? = null,
    countDownIntervalInMilli:Long?=null
): CountDownTimer = object : CountDownTimer(toMillis(), countDownIntervalInMilli?:toMillis()) {
    override fun onTick(millisUntilFinished: Long) {
        onTicked?.invoke(millisUntilFinished.toDuration())
    }

    override fun onFinish() {
        onFinished?.invoke()
    }
}.start()