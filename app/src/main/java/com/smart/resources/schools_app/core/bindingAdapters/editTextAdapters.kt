package com.smart.resources.schools_app.core.bindingAdapters

import android.transition.TransitionManager
import android.view.ViewGroup
import android.widget.EditText
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout
import com.haytham.coder.extensions.toString

private const val EDIT_TEXT_ERROR_MSG_ATTRIBUTE = "android:errorMsg"

@BindingAdapter(EDIT_TEXT_ERROR_MSG_ATTRIBUTE)
fun setEditTextErrorMsg(editText: EditText, errorMsg: String?) {
    editText.error = errorMsg
}

@BindingAdapter(EDIT_TEXT_ERROR_MSG_ATTRIBUTE)
fun EditText.setEditTextError(errorMsgId: Int?) {
    error = errorMsgId?.toString(context)
}


@BindingAdapter(EDIT_TEXT_ERROR_MSG_ATTRIBUTE)
fun TextInputLayout.setInputLayoutError(errorMsg: String?) {
    error = errorMsg
}

@BindingAdapter(EDIT_TEXT_ERROR_MSG_ATTRIBUTE)
fun TextInputLayout.setInputLayoutError(errorMsgId: Int?) {
    if (errorMsgId == null && error == null) return

    val layout = rootView
    if (layout is ViewGroup) {
        TransitionManager.beginDelayedTransition(layout)
    }
    isErrorEnabled = errorMsgId != null
    error = errorMsgId?.toString(context)
}


