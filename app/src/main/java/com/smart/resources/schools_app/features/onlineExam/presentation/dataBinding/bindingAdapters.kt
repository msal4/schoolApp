package com.smart.resources.schools_app.features.onlineExam.presentation.dataBinding

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.extentions.toString

@BindingAdapter("android:answer")
fun TextView.answer(answer:String?){
    text = if(answer.isNullOrBlank()) R.string.no_answer.toString(context) else answer
}
