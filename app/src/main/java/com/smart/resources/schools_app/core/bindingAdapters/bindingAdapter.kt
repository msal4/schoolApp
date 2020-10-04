package com.smart.resources.schools_app.core.bindingAdapters

import android.transition.TransitionManager
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.RatingBar
import androidx.core.view.children
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.haytham.coder.extensions.hide
import com.haytham.coder.extensions.show
import com.smart.resources.schools_app.core.callbacks.SwipeAdapter

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

@BindingAdapter("android:animatedVisible")
fun View.setAnimatedVisible(visible: Boolean?){
    val root= rootView
    if(root is ViewGroup){
        TransitionManager.beginDelayedTransition(root)
    }
    setVisible(visible)
}

@BindingAdapter("android:radioGroupReadOnly")
fun RadioGroup.setRadioGroupReadOnly(readOnly: Boolean?){
    children.forEach {
        it.isClickable= readOnly==false
    }
}

