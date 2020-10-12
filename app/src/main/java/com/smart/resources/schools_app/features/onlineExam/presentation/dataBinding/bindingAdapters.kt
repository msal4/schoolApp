package com.smart.resources.schools_app.features.onlineExam.presentation.dataBinding

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.haytham.coder.extensions.toString
import com.haytham.coder.extensions.unicodeWrap
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.typeConverters.room.QuestionType

@BindingAdapter("android:answer")
fun TextView.setAnswer(answer:String?){
    text = if(answer.isNullOrBlank()) R.string.no_answer.toString(context) else answer
}


@BindingAdapter("android:questionText", "android:questionType")
fun TextView.setQuestionText(question:String?, questionType: QuestionType?){
    text= when(questionType){
        QuestionType.MULTI_CHOICE -> question
        QuestionType.CORRECTNESS -> question
        QuestionType.DEFINE -> "${R.string.define.toString(context)}/ $question"
        QuestionType.WHY -> "${R.string.why_question.toString(context)}/ $question"
        QuestionType.ANSWER_THE_FOLLOWING -> "${R.string.answer_the_following.toString(context)}/ $question"
        else -> question
    }
}