package com.smart.resources.schools_app.adapter

import android.widget.EditText
import androidx.databinding.BindingAdapter

@BindingAdapter("mine:errorMsg")
fun setEditTextError(editText: EditText, errorMsg:String?){
    editText.error= errorMsg
}
