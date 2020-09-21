package com.smart.resources.schools_app.core.bindingAdapters

import android.graphics.Color
import android.view.View
import android.widget.*
import androidx.databinding.BindingAdapter
import com.orhanobut.logger.Logger
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.extentions.toColor
import com.smart.resources.schools_app.core.extentions.toDimen
import com.smart.resources.schools_app.core.extentions.toDrawable
import com.smart.resources.schools_app.features.onlineExam.domain.model.OnlineExamStatus

@BindingAdapter("mine:setStars")
fun setStars(ratingBar:  RatingBar, stars:Int?){
    ratingBar.rating= (stars?:0).toFloat()
}




