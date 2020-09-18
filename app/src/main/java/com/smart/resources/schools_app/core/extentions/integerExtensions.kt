package com.smart.resources.schools_app.core.extentions

import android.content.Context
import android.content.res.Resources
import androidx.core.content.ContextCompat

fun Int.toColor(context: Context) = ContextCompat.getColor(context, this)

fun Int.toDimen(context: Context) = context.resources.getDimension(this)

fun Int.toDrawable(context: Context) = context.resources.getDrawable(this, null)

fun pxToDp(px: Int): Int {
    return (px / Resources.getSystem().displayMetrics.density).toInt()
}

fun dpToPx(dp: Int): Int {

    return (dp * Resources.getSystem().displayMetrics.density).toInt()
}
