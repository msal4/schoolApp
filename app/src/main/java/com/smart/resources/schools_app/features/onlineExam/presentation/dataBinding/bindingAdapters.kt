package com.smart.resources.schools_app.features.onlineExam.presentation.dataBinding

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.haytham.coder.extensions.toString
import com.smart.resources.schools_app.R

@BindingAdapter("android:answer")
fun TextView.answer(answer:String?){
    text = if(answer.isNullOrBlank()) R.string.no_answer.toString(context) else answer
}
