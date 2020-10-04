package com.smart.resources.schools_app.core.extentions

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.smart.resources.schools_app.R


fun Context.toast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
}

val Context.selectableItemBackgroundBorderless: Int
    get() {
        val outValue = TypedValue()
        theme.resolveAttribute(
            android.R.attr.selectableItemBackgroundBorderless,
            outValue,
            true
        )
        return outValue.resourceId
    }

val Context.activity: AppCompatActivity?
    get() {
        var context = this
        while (context is ContextWrapper) {
            if (context is AppCompatActivity) {
                return context
            }
            context = context.baseContext
        }
        return null
    }

fun Context.openKeyboard() {
    val imm: InputMethodManager? =
        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
    imm?.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
}

fun View.hide() {
    visibility = View.GONE
}

fun View.show() {
    visibility = View.VISIBLE
}

fun ViewGroup.showSnackBar(msg: String, isError: Boolean = true) {
    Snackbar.make(this, msg, Snackbar.LENGTH_LONG).apply {
        val color = if (isError) Color.RED else R.color.light_green_a700
        setTextColor(color)
        setBackgroundTint(R.color.snackbar_background.toColor(context))
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


fun Window.hideSystemUi() {
    decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
            or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            or View.SYSTEM_UI_FLAG_FULLSCREEN)
}