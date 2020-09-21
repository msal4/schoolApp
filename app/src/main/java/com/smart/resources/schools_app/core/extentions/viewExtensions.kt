package com.smart.resources.schools_app.core.extentions

import android.content.Context
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.core.view.forEach
import androidx.core.view.get
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.orhanobut.logger.Logger
import com.smart.resources.schools_app.R

fun Context.toast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
}

val Context.selectableItemBackgroundBorderless:Int get() {
    val outValue = TypedValue()
    theme.resolveAttribute(
        android.R.attr.selectableItemBackgroundBorderless,
        outValue,
        true
    )
    return outValue.resourceId
}

fun View.hide() {
    visibility = View.GONE
}

fun View.show() {
    visibility = View.VISIBLE
}

fun ViewGroup.showSnackBar(msg: String, isError: Boolean = true) {
    val snackBar = Snackbar.make(this, msg, Snackbar.LENGTH_LONG)
    val snackView: ViewGroup = snackBar.view as ViewGroup
    snackView.layoutDirection = View.LAYOUT_DIRECTION_RTL
    snackView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimaryDark))
    (snackView[0] as ViewGroup).forEach {
        Logger.i("type " + it.javaClass.name)
        if (it is TextView) {
            it.setTextColor(
                if (isError) ContextCompat.getColor(
                    context,
                    R.color.darkRed
                ) else ContextCompat.getColor(context, R.color.green)
            )
        }
    }
    snackBar.show()
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


fun Window.hideSystemUi() {
    decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
            // Set the content to appear under the system bars so that the
            // content doesn't resize when the system bars hide and show.
            or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            // Hide the nav bar and status bar
            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            or View.SYSTEM_UI_FLAG_FULLSCREEN)
}