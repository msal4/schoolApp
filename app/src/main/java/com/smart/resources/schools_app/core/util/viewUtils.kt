package com.smart.resources.schools_app.core.util

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.forEach
import androidx.core.view.get
import com.google.android.material.snackbar.Snackbar
import com.orhanobut.logger.Logger

fun Context.toast(msg:String){
    Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
}

fun ProgressBar.hide(){
    visibility= View.GONE
}
fun ProgressBar.show(){
    visibility= View.VISIBLE
}

fun ViewGroup.showErrorSnackbar(msg:String){
    val snackBar = Snackbar.make(this, msg, Snackbar.LENGTH_LONG)
    val snackView: ViewGroup = snackBar.view as ViewGroup
    snackView.layoutDirection = View.LAYOUT_DIRECTION_RTL
    snackView.setBackgroundColor(Color.WHITE)
    (snackView[0] as ViewGroup).forEach {
        Logger.i("type " + it.javaClass.name)
        if (it is TextView) {
            it.setTextColor(Color.RED)
        }
    }
    snackBar.show()
}