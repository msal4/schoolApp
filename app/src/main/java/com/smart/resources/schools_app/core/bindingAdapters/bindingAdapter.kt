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


@BindingAdapter("android:onlineExamCardStatus")
fun View.setOnlineExamCardStatus(onlineExamStatus: OnlineExamStatus?){

    background= when(onlineExamStatus){
        OnlineExamStatus.READY -> R.drawable.background_online_exam_ready
        OnlineExamStatus.IN_PROGRESS -> R.drawable.background_online_exam_in_progress
        else -> R.drawable.background_item
    }.toDrawable(context)

    elevation = when(onlineExamStatus){
        OnlineExamStatus.READY ->  R.dimen.exam_ready_item_elevation
        OnlineExamStatus.IN_PROGRESS ->  R.dimen.exam_in_progress_item_elevation
        else -> R.dimen.item_elevation
    }.toDimen(context)

    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
        val color= when(onlineExamStatus){
            OnlineExamStatus.READY -> R.color.examReadyColor
            OnlineExamStatus.IN_PROGRESS -> R.color.examInProgressColor
            else -> android.R.color.black
        }.toColor(context)

        outlineAmbientShadowColor= color
        outlineSpotShadowColor= color
    }

}

