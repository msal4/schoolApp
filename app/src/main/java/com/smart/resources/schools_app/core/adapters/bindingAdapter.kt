package com.smart.resources.schools_app.core.adapters

import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputLayout
import com.orhanobut.logger.Logger
import com.smart.resources.schools_app.core.helpers.SharedPrefHelper
import com.smart.resources.schools_app.core.myTypes.UserType
import com.tiper.MaterialSpinner
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.LocalTime
import java.lang.Exception

@BindingAdapter("mine:errorMsg")
fun setEditTextError(editText: EditText, errorMsg:String?){
    editText.error= errorMsg
}

@BindingAdapter("mine:errorMsg")
fun setInputLayoutError(inputLayout: TextInputLayout, errorMsg:String?){
    inputLayout.error= errorMsg
}

@BindingAdapter("mine:errorMsg")
fun setSpinnerError(spinner: MaterialSpinner, errorMsg:String?){
    spinner.error= errorMsg
}

@BindingAdapter("mine:setStars")
fun setStars(ratingBar:  RatingBar, stars:Int?){
    ratingBar.rating= (stars?:0).toFloat()
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
fun setDateFomText(textView: TextView): LocalDateTime{
    return LocalDateTime.of(LocalDate.parse(textView.text, dateDisFormatter), LocalTime.of(0, 0))
}



@BindingAdapter("mine:setDateAttrChanged")
fun setListeners(
    view: TextView,
    attrChange: InverseBindingListener
) {
    view.addTextChangedListener(object :TextWatcher{
        override fun afterTextChanged(s: Editable?) {
            attrChange.onChange()
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

    })
}


@BindingAdapter("android:srcUrl")
fun loadImageUrl(iv: ImageView, url: String?) {
    //iv.context.toast(url.toString())
    Glide
        .with(iv.context)
        .load(url)
        .into(iv)
}

@BindingAdapter("android:setMark")
fun setMark(tv: TextView, mark:Int?) {
    val isStudent= SharedPrefHelper.instance?.userType== UserType.STUDENT
        tv.text = mark?.toString() ?: if(isStudent)"- " else ""
}


@BindingAdapter("android:setList")
fun <T>setSpinnerList(spinner: MaterialSpinner, list:List<T>) {


    val adapter = ArrayAdapter<T>(
        spinner.context,
        android.R.layout.simple_spinner_item, list
    )

    spinner.adapter= adapter
}

