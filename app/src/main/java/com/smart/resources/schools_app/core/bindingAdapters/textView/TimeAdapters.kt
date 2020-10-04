package com.smart.resources.schools_app.core.bindingAdapters.textView

import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.haytham.coder.extensions.activity
import com.smart.resources.schools_app.core.typeConverters.retrofit.timeDisFormatter
import com.smart.resources.schools_app.features.dateTimePickers.TimePickerFragment
import org.threeten.bp.LocalTime
import java.lang.Exception

private const val SET_TIME_ATTRIBUTE = "android:setTime"
private const val SHOW_TIME_PICKER_ON_CLICK_ATTRIBUTE = "android:showTimePickerOnClick"

@BindingAdapter(SET_TIME_ATTRIBUTE)
fun TextView.setTime(time: LocalTime?) {
    text = time?.format(timeDisFormatter)
}

@InverseBindingAdapter(attribute = SET_TIME_ATTRIBUTE)
fun TextView.toLocalTime(): LocalTime? {
    return  try {
         LocalTime.parse(
            text,
            timeDisFormatter
        )
    }catch (e:Exception){
        null
    }
}

@BindingAdapter("${SET_TIME_ATTRIBUTE}AttrChanged")
fun TextView.setTimeChangedListener(
    attrChange: InverseBindingListener
) {
    var prevText= text
    doAfterTextChanged {
        if(prevText!=text) {
            prevText= text
            attrChange.onChange()
        }
    }
}
@BindingAdapter(SHOW_TIME_PICKER_ON_CLICK_ATTRIBUTE)
fun TextView.setShowTimePickerOnClick(enabled: Boolean) {
    if (enabled) {
        setOnClickListener {
            val pickerFragment = TimePickerFragment.newInstance()
            pickerFragment.onTimeSet = {
                setTime(it)
            }
            context.activity?.let {
                pickerFragment.show(it.supportFragmentManager, "")
            }
        }
    } else {
        setOnClickListener(null)
    }
}