package com.smart.resources.schools_app.util

import android.content.Context
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast

fun Context.toast(msg:String){
    Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
}

fun ProgressBar.hide(){
    visibility= View.GONE
}
fun ProgressBar.show(){
    visibility= View.VISIBLE
}