package com.smart.resources.schools_app.features.onlineExam.presentation.dataBinding

import android.view.View
import androidx.databinding.BindingAdapter
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.extentions.*
import com.smart.resources.schools_app.core.typeConverters.room.OnlineExamStatus

@BindingAdapter("android:onlineExamCardStatus", "android:canTakeOnlineExam", requireAll = true)
fun View.setOnlineExamCardStatus(onlineExamStatus: OnlineExamStatus, canTakeOnlineExam:Boolean){
    if(!canTakeOnlineExam){
        elevation= R.dimen.item_elevation.toDimen(context)
        background = R.drawable.background_card.toDrawableResource(context)
        return
    }

    background= when(onlineExamStatus){
        OnlineExamStatus.ACTIVE -> R.drawable.background_online_exam_active
        else -> R.drawable.background_card
    }.toDrawableResource(context)

    elevation = when(onlineExamStatus){
        OnlineExamStatus.ACTIVE ->  R.dimen.exam_item_active_elevation
        else -> R.dimen.item_elevation
    }.toDimen(context)

    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
        val color= when(onlineExamStatus){
            OnlineExamStatus.ACTIVE -> R.color.examActiveColor
            else -> android.R.color.black
        }.toColorResource(context)

        outlineAmbientShadowColor= color
        outlineSpotShadowColor= color
    }
}

