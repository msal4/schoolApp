package com.smart.resources.schools_app.core.utils

import android.content.Context
import androidx.core.content.ContextCompat
import com.smart.resources.schools_app.R

fun Context.defaultGradient() = intArrayOf(
    ContextCompat.getColor(this, R.color.colorPrimaryDark),
    ContextCompat.getColor(this, R.color.colorPrimaryLight))