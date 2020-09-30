package com.smart.resources.schools_app.core.extentions

import android.app.Activity
import android.graphics.Color
import android.view.View
import android.view.WindowManager
import androidx.annotation.ColorInt

/**
 * if we try to change status bar color, it won't work until this view is disappeared
 */
fun Activity.setStatusBarColorToWhite(view: View) {
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = Color.WHITE

        var flags = view.systemUiVisibility
        flags = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        view.systemUiVisibility = flags
    }
}

fun Activity.setStatusBarColor(@ColorInt color: Int) {
    window.apply {
        addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        statusBarColor = color
    }
}

fun Activity.setSoftInputMode(softInputMode: Int) {
    window.setSoftInputMode(softInputMode)
}

