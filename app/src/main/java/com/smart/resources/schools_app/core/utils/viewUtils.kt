package com.smart.resources.schools_app.core.utils

import android.content.Context
import android.graphics.Color
import android.graphics.Color.green
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.graphics.green
import androidx.core.view.forEach
import androidx.core.view.get
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.orhanobut.logger.Logger
import com.smart.resources.schools_app.R

fun Context.toast(msg:String){
    Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
}

fun ProgressBar.hide(){
    visibility= View.GONE
}
fun ProgressBar.show(){
    visibility= View.VISIBLE
}

fun ViewGroup.showSnackBar(msg:String, isError: Boolean= true){
    val snackBar = Snackbar.make(this, msg, Snackbar.LENGTH_LONG)
    val snackView: ViewGroup = snackBar.view as ViewGroup
    snackView.layoutDirection = View.LAYOUT_DIRECTION_RTL
    snackView.setBackgroundColor(Color.WHITE)
    (snackView[0] as ViewGroup).forEach {
        Logger.i("type " + it.javaClass.name)
        if (it is TextView) {
            it.setTextColor(if(isError) Color.rgb(150, 0, 0) else Color.rgb(0, 120, 0))
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