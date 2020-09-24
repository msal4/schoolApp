package com.smart.resources.schools_app.core.bindingAdapters

import android.widget.ArrayAdapter
import androidx.databinding.BindingAdapter
import com.tiper.MaterialSpinner

@BindingAdapter("android:setList")
fun <T>setSpinnerList(spinner: MaterialSpinner, list:List<T>) {
    ArrayAdapter<T>(
        spinner.context,
        android.R.layout.simple_spinner_item, list
    ).apply {
        setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter= this
    }
}


@BindingAdapter("android:errorMsg")
fun setSpinnerError(spinner: MaterialSpinner, errorMsg:String?){
    spinner.error= errorMsg
}