package com.smart.resources.schools_app.core.bindingAdapters

import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.RatingBar
import androidx.core.view.children
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.smart.resources.schools_app.core.callbacks.SwipeAdapter

@BindingAdapter("mine:setStars")
fun setStars(ratingBar:  RatingBar, stars:Int?){
    ratingBar.rating= (stars?:0).toFloat()
}


@BindingAdapter("android:checkedButtonIndex")
fun RadioGroup.setCheckedButtonIndex(checkButtonIndex:Int){
    val radioBtnId= children.filter { it is RadioButton }.toList()[checkButtonIndex].id
    check(radioBtnId)
}

fun interface SwipeListener{
    fun onSwiped(position:Int)
}

@BindingAdapter("android:swipeToDelete", "android:extraItemsCount", requireAll = false)
fun RecyclerView.setSwipeToDelete(swipeListener: SwipeListener, extraItemsCount:Int?=0){
    val swipeAdapter = SwipeAdapter {
        swipeListener.onSwiped(it.adapterPosition - (extraItemsCount?:0))
    }
    ItemTouchHelper(swipeAdapter).attachToRecyclerView(this)
}
