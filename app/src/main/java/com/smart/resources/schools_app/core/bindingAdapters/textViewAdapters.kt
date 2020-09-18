package com.smart.resources.schools_app.core.bindingAdapters

import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.orhanobut.logger.Logger
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.adapters.dateDisFormatter
import com.smart.resources.schools_app.features.users.UsersRepository
import kotlinx.android.synthetic.main.item_online_exam.view.*
import org.threeten.bp.Duration
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.LocalTime
import java.lang.Exception

@BindingAdapter("android:setMark")
fun setMark(tv: TextView, mark:Int?) {
    val isStudent= UsersRepository.instance.getCurrentUserAccount()?.userType==0
    tv.text = mark?.toString() ?: if(isStudent)"- " else ""
}

@BindingAdapter("android:numberOfQuestions")
fun TextView.setNumberOfQuestions(numberOfQuestions:Int?) {
    text= context.getString(R.string.numberOfQuestions, numberOfQuestions?:0)
}

@BindingAdapter("android:durationInMinutes")
fun TextView.setDurationInMinutes(duration: Duration?) {
    text= context.getString(R.string.durationInMinutes, duration?.toMinutes()?:0)
}

@BindingAdapter("mine:setDate")
fun setTextFromDate(textView: TextView, date: LocalDateTime?){
    try {
        textView.text = date?.format(dateDisFormatter)
    }catch (e: Exception){
        Logger.e("binding error: ${e.message}")
    }

}

@InverseBindingAdapter(attribute = "mine:setDate")
fun setDateFomText(textView: TextView): LocalDateTime {
    return LocalDateTime.of(
        LocalDate.parse(textView.text,
            dateDisFormatter
        ), LocalTime.of(0, 0))
}

@BindingAdapter("mine:setDateAttrChanged")
fun setListeners(
    view: TextView,
    attrChange: InverseBindingListener
) {
    view.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            attrChange.onChange()
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

    })
}