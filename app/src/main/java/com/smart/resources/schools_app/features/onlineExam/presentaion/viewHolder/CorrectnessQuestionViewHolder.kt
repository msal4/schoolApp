package com.smart.resources.schools_app.features.onlineExam.presentaion.viewHolder

import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.extentions.hide
import com.smart.resources.schools_app.core.extentions.show
import com.smart.resources.schools_app.databinding.PageQuestionCorrectnessBinding
import com.smart.resources.schools_app.features.onlineExam.domain.model.questions.CorrectnessQuestion

class CorrectnessQuestionViewHolder(var binding: PageQuestionCorrectnessBinding) :
    RecyclerView.ViewHolder(binding.root) {
    var onQuestionAnswerStateUpdated: (() -> Unit)? = null
    private var oldIsAnswered= false

    fun setup(question: CorrectnessQuestion) {
        binding.apply {
            isCorrectRadioGroup.setOnCheckedChangeListener { _, checkedId ->
                TransitionManager.beginDelayedTransition(root as ViewGroup)
                question.answer= if (checkedId == R.id.incorrectRadioBtn) {
                    answerLayout.show()
                    val correctAnswer= answer.text?.trim()?.toString()?:""
                    question.correctAnswer= correctAnswer
                    false
                } else {
                    answerLayout.hide()
                     true
                }
                if(question.isAnswered != oldIsAnswered) {
                    onQuestionAnswerStateUpdated?.invoke()
                    oldIsAnswered= question.isAnswered
                }
            }
        }
    }

    private fun bind(question: CorrectnessQuestion) {
        binding.apply {
            questionModel = question
            executePendingBindings()
        }
    }

    companion object {
        fun create(parent: ViewGroup) = CorrectnessQuestionViewHolder(
            PageQuestionCorrectnessBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
        )
    }
}