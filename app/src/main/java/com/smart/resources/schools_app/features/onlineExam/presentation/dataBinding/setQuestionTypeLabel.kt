package com.smart.resources.schools_app.features.onlineExam.presentation.dataBinding

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.haytham.coder.extensions.toColorStateList
import com.haytham.coder.extensions.toString
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.typeConverters.room.QuestionType

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
