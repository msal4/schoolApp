package com.smart.resources.schools_app.core

import android.content.Context
import androidx.core.content.ContextCompat
import com.smart.resources.schools_app.R

fun Context.defaultGradient() = intArrayOf(
    ContextCompat.getColor(this, R.color.colorPrimary),
    ContextCompat.getColor(this, R.color.colorAccent))