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
        QuestionType.MULTI_CHOICE -> R.string.options
        QuestionType.CORRECTNESS -> R.string.true_and_false
        QuestionType.DEFINE -> R.string.define
        QuestionType.WHY -> R.string.why_question
        QuestionType.ANSWER_THE_FOLLOWING -> R.string.answer_the_following
    }.toString(context)

    backgroundTintList = when(questionType){
        QuestionType.CORRECTNESS -> R.color.questionCorrectnessColor
        QuestionType.MULTI_CHOICE -> R.color.questionMultiChoiceColor
        QuestionType.DEFINE -> R.color.questionDefineColor
        QuestionType.WHY -> R.color.questionWhyColor
        QuestionType.ANSWER_THE_FOLLOWING -> R.color.questionAnswerColor
    }.toColorStateList(context)
}
