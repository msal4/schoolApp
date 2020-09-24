package com.smart.resources.schools_app.core.bindingAdapters

import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.RatingBar
import androidx.core.view.children
import androidx.databinding.BindingAdapter

@BindingAdapter("mine:setStars")
fun setStars(ratingBar:  RatingBar, stars:Int?){
    ratingBar.rating= (stars?:0).toFloat()
}


@BindingAdapter("android:checkedButtonIndex")
fun RadioGroup.setCheckedButtonIndex(checkButtonIndex:Int){
    val radioBtnId= children.filter { it is RadioButton }.toList()[checkButtonIndex].id
    check(radioBtnId)
}


