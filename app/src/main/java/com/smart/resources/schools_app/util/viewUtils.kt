package com.smart.resources.schools_app.util

import android.content.Context
import android.widget.Toast

fun Context.toast(msg:String){
    Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
}