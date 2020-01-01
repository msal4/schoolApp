package com.smart.resources.schools_app.adapter

import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.orhanobut.logger.Logger
import com.smart.resources.schools_app.util.dateDisFormatter
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import java.lang.Exception

@BindingAdapter("mine:errorMsg")
fun setEditTextError(editText: EditText, errorMsg:String?){
    editText.error= errorMsg
}



@BindingAdapter("mine:setDate")
fun setDate(textView: TextView, date: LocalDateTime){
    try {
        textView.text = date.format(dateDisFormatter)
    }catch (e: Exception){
        Logger.e("binding error: ${e.message}")
    }

}

@BindingAdapter("android:srcUrl")
fun loadImageUrl(iv: ImageView, url: String) {
    Glide
        .with(iv.context)
        .load(url)
        .into(iv)
}
