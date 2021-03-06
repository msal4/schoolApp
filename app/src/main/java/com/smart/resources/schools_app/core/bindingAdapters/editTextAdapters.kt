package com.smart.resources.schools_app.core.bindingAdapters

import android.transition.TransitionManager
import android.view.ViewGroup
import android.widget.EditText
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout
import com.haytham.coder.extensions.toString

private const val EDIT_TEXT_ERROR_MSG_ATTRIBUTE = "android:errorMsg"
private const val EDIT_TEXT_ERROR_MSG_ID_ATTRIBUTE = "android:errorMsgId"

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

@BindingAdapter(EDIT_TEXT_ERROR_MSG_ID_ATTRIBUTE)
fun TextInputLayout.setInputLayoutError(errorMsgId: Int?) {
    val layout = rootView
    if (layout is ViewGroup) {
        TransitionManager.beginDelayedTransition(layout)
    }

    // clear error msg before setting another one to avoid error msg being cut problem
    error = null
    isErrorEnabled = false

    error = errorMsgId?.toString(context)
}


///**
// * used for edit text that is automatically reassigned when entering new text (for example from database LiveData...)
// * - it prevent setting duplicate values
// * - fixes cursor location issues
// */
//@BindingAdapter("android:syncableText")
//fun EditText.setSyncableText(text: String?) {
//    if(text!=this.text.toString()){
//        setText(text)
//       // setSelection(this.text?.length?:0)
//    }
//}

