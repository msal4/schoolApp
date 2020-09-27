package com.smart.resources.schools_app.core.bindingAdapters

import android.transition.TransitionManager
import android.view.ViewGroup
import android.widget.EditText
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout
import com.smart.resources.schools_app.core.extentions.toStringResource

@BindingAdapter("android:errorMsg")
fun setEditTextErrorMsg(editText: EditText, errorMsg:String?){
    editText.error= errorMsg
}

@BindingAdapter("android:errorMsg")
fun EditText.setEditTextError(errorMsgId:Int?){
   error= errorMsgId?.toStringResource(context)
}


@BindingAdapter("android:errorMsg")
fun setInputLayoutError(inputLayout: TextInputLayout, errorMsg:String?){
    inputLayout.error= errorMsg
}

@BindingAdapter("android:errorMsg")
fun TextInputLayout.setEditTextError(errorMsgId:Int?){
    if(errorMsgId == null && error == null) return

    val layout= rootView
    if(layout is ViewGroup){
        TransitionManager.beginDelayedTransition(layout)
    }
    isErrorEnabled= errorMsgId != null
    error= errorMsgId?.toStringResource(context)
}
