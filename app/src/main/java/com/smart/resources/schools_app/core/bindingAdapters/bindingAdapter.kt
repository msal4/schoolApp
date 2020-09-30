package com.smart.resources.schools_app.core.bindingAdapters

import android.view.View
import android.widget.*
import androidx.core.view.children
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.smart.resources.schools_app.core.callbacks.SwipeAdapter
import com.smart.resources.schools_app.core.extentions.hide
import com.smart.resources.schools_app.core.extentions.show

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

@BindingAdapter("android:swipeToDelete", "android:headersCount", requireAll = false)
fun RecyclerView.setSwipeToDelete(swipeListener: SwipeListener, headersCount:Int?=0){
    val fixedPositions= if(headersCount!=null) (0 until headersCount).toList() else emptyList()

    val swipeAdapter = SwipeAdapter(fixedPositions) {
        swipeListener.onSwiped(it.adapterPosition - (headersCount?:0))
    }
    ItemTouchHelper(swipeAdapter).attachToRecyclerView(this)
}


@BindingAdapter("android:visible")
fun View.setVisible(visible: Boolean?){
    if(visible == true){
        show()
    }else{
        hide()
    }
}

@BindingAdapter("android:radioGroupReadOnly")
fun RadioGroup.setRadioGroupReadOnly(readOnly: Boolean?){
    children.forEach {
        it.isClickable= readOnly==false
    }
}

