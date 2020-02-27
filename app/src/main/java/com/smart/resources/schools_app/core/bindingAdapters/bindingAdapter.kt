package com.smart.resources.schools_app.core.bindingAdapters

import android.widget.*
import androidx.databinding.BindingAdapter

@BindingAdapter("mine:setStars")
fun setStars(ratingBar:  RatingBar, stars:Int?){
    ratingBar.rating= (stars?:0).toFloat()
}




