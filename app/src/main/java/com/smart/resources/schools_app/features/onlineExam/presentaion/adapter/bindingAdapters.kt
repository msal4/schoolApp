package com.smart.resources.schools_app.features.onlineExam.presentaion.adapter

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.extentions.*
import com.smart.resources.schools_app.features.onlineExam.domain.model.OnlineExamStatus
import com.smart.resources.schools_app.features.onlineExam.domain.model.QuestionType

@BindingAdapter("android:onlineExamCardStatus", "android:canTakeOnlineExam", requireAll = true)
fun View.setOnlineExamCardStatus(onlineExamStatus: OnlineExamStatus, canTakeOnlineExam:Boolean){
    if(!canTakeOnlineExam){
        elevation= R.dimen.item_elevation.toDimen(context)
        background = R.drawable.background_card.toDrawable(context)
        return
    }

    background= when(onlineExamStatus){
        OnlineExamStatus.READY -> R.drawable.background_online_exam_ready
        OnlineExamStatus.IN_PROGRESS -> R.drawable.background_online_exam_in_progress
        else -> R.drawable.background_card
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

@BindingAdapter("android:questionType")
fun TextView.setQuestionTypeLabel(questionType: QuestionType){
    text = when(questionType){
        QuestionType.NORMAL -> R.string.normal
        QuestionType.CORRECTNESS -> R.string.true_and_false
        QuestionType.MULTI_CHOICE -> R.string.options
    }.toString(context)

    backgroundTintList = when(questionType){
        QuestionType.NORMAL -> R.color.questionNormalColor
        QuestionType.CORRECTNESS -> R.color.questionCorrectnessColor
        QuestionType.MULTI_CHOICE -> R.color.questionMultiChoiceColor
    }.toColorStateList(context)
}
