package com.smart.resources.schools_app.core.bindingAdapters

import android.widget.EditText
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("android:errorMsg")
fun setEditTextError(editText: EditText, errorMsg:String?){
    editText.error= errorMsg
}


@BindingAdapter("android:errorMsg")
fun setInputLayoutError(inputLayout: TextInputLayout, errorMsg:String?){
    inputLayout.error= errorMsg
}