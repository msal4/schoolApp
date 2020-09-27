package com.smart.resources.schools_app.features.onlineExam.presentation.viewHolder

import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.extentions.hide
import com.smart.resources.schools_app.core.extentions.show
import com.smart.resources.schools_app.databinding.PageQuestionCorrectnessBinding
import com.smart.resources.schools_app.features.onlineExam.domain.model.CorrectnessAnswerableQuestion
import com.smart.resources.schools_app.features.onlineExam.domain.model.CorrectnessAnswer

class CorrectnessQuestionViewHolder(var binding: PageQuestionCorrectnessBinding) :
    RecyclerView.ViewHolder(binding.root) {
    var onQuestionAnswerUpdated: ((correctnessAnswer: CorrectnessAnswer) -> Unit)? = null

    fun setup(answerableQuestion: CorrectnessAnswerableQuestion) {
        binding.apply {
            isCorrectRadioGroup.setOnCheckedChangeListener { _, checkedId ->
                TransitionManager.beginDelayedTransition(root as ViewGroup)
                val answer = if (checkedId == R.id.incorrectRadioBtn) {
                    answerLayout.show()
                    val correctAnswer = this.answerEditText.text?.trim()?.toString() ?: ""
                    CorrectnessAnswer(answer = false, correctAnswer = correctAnswer)
                } else {
                    answerLayout.hide()
                    CorrectnessAnswer(answer = false,)
                }

                onQuestionAnswerUpdated?.invoke(answer)
            }

            bind(answerableQuestion)
        }
    }

    private fun bind(answerableQuestion: CorrectnessAnswerableQuestion) {
        binding.apply {
            questionModel = answerableQuestion
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