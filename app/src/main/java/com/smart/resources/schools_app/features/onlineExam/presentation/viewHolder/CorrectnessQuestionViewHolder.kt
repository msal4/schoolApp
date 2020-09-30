package com.smart.resources.schools_app.features.onlineExam.presentation.viewHolder

import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.extentions.hide
import com.smart.resources.schools_app.core.extentions.show
import com.smart.resources.schools_app.databinding.PageQuestionCorrectnessBinding
import com.smart.resources.schools_app.features.onlineExam.domain.model.CorrectnessAnswer
import com.smart.resources.schools_app.features.onlineExam.domain.model.CorrectnessAnswerableQuestion

class CorrectnessQuestionViewHolder(
    val binding: PageQuestionCorrectnessBinding,
    private val readOnly: Boolean
) :
    RecyclerView.ViewHolder(binding.root) {
    var onQuestionAnswerUpdated: ((correctnessAnswer: CorrectnessAnswer) -> Unit)? = null

    fun setup(answerableQuestion: CorrectnessAnswerableQuestion) {
        binding.apply {
            isCorrectRadioGroup.setOnCheckedChangeListener { _, checkedId ->
                TransitionManager.beginDelayedTransition(root as ViewGroup)
                val answer = if (checkedId == R.id.incorrectRadioBtn) {
                    showAnswer()

                    val correctAnswer = this.answerEditText.text?.trim()?.toString() ?: ""
                    CorrectnessAnswer(answer = false, correctAnswer = correctAnswer)
                } else {
                    hideAnswer()
                    CorrectnessAnswer(answer = false)
                }

                onQuestionAnswerUpdated?.invoke(answer)
            }

            bind(answerableQuestion)
        }
    }

    private fun hideAnswer() {
        if (readOnly) {
            binding.answerText.hide()
        } else {
            binding.answerLayout.hide()
        }
    }

    private fun showAnswer() {
        if (readOnly) {
            binding.answerText.show()
        } else {
            binding.answerLayout.show()
        }
    }

    private fun bind(answerableQuestion: CorrectnessAnswerableQuestion) {
        binding.apply {
            questionModel = answerableQuestion
            readOnly = this@CorrectnessQuestionViewHolder.readOnly
            executePendingBindings()
        }
    }

    companion object {
        fun create(parent: ViewGroup, readOnly: Boolean) = CorrectnessQuestionViewHolder(
            PageQuestionCorrectnessBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            readOnly
        )
    }
}