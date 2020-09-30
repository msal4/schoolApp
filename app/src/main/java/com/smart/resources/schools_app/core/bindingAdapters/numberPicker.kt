package com.smart.resources.schools_app.core.bindingAdapters

import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import it.sephiroth.android.library.numberpicker.NumberPicker
import it.sephiroth.android.library.numberpicker.doOnProgressChanged

private const val NUMBER_PICKER_PROGRESS_VALUE_ATTRIBUTE= "android:progressValue"
@BindingAdapter(NUMBER_PICKER_PROGRESS_VALUE_ATTRIBUTE)
fun NumberPicker.setProgressValue(int: Int?){
    progress= int?:0
}

@InverseBindingAdapter(attribute = NUMBER_PICKER_PROGRESS_VALUE_ATTRIBUTE)
fun NumberPicker.getProgressValue():Int{
    return progress
}

@BindingAdapter("${NUMBER_PICKER_PROGRESS_VALUE_ATTRIBUTE}AttrChanged")
fun NumberPicker.setProgressValueChangedListener(
    attrChange: InverseBindingListener
) {
    var prevProgress= progress
    doOnProgressChanged { _, progress, _ ->
        if(prevProgress!=progress) {
            prevProgress= progress
            attrChange.onChange()
        }
    }
}