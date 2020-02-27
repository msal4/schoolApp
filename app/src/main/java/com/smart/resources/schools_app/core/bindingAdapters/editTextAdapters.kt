package com.smart.resources.schools_app.core.bindingAdapters

import android.widget.EditText
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("mine:errorMsg")
fun setEditTextError(editText: EditText, errorMsg:String?){
    editText.error= errorMsg
}


@BindingAdapter("mine:errorMsg")
fun setInputLayoutError(inputLayout: TextInputLayout, errorMsg:String?){
    inputLayout.error= errorMsg
}