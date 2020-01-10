package com.smart.resources.schools_app.core.adapters

import android.R
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.orhanobut.logger.Logger
import com.tiper.MaterialSpinner
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
fun loadImageUrl(iv: ImageView, url: String?) {
    //iv.context.toast(url.toString())
    Glide
        .with(iv.context)
        .load(url)
        .into(iv)
}

@BindingAdapter("android:setMark")
fun setMark(tv: TextView, mark:Int?) {
    tv.text= mark?.toString() ?: "- "
}


@BindingAdapter("android:setList")
fun <T>setSpinnerList(spinner: MaterialSpinner, list:List<T>) {


    val adapter = ArrayAdapter<T>(
        spinner.context,
        R.layout.simple_spinner_item, list
    )

    spinner.adapter= adapter
}
