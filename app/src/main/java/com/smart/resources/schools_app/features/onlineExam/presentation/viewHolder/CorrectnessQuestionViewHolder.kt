package com.smart.resources.schools_app.features.onlineExam.presentation.viewHolder

import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.haytham.coder.extensions.hide
import com.haytham.coder.extensions.show
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.databinding.PageQuestionCorrectnessBinding
import com.smart.resources.schools_app.features.onlineExam.domain.model.CorrectnessAnswer
import com.smart.resources.schools_app.features.onlineExam.domain.model.CorrectnessAnswerableQuestion

class CorrectnessQuestionViewHolder(
    val binding: PageQuestionCorrectnessBinding
): RecyclerView.ViewHolder(binding.root) {
    var onQuestionAnswerUpdated: ((correctnessAnswer: CorrectnessAnswer) -> Unit)? = null

    fun setup(answerableQuestion: CorrectnessAnswerableQuestion, readOnly: Boolean) {
        binding.apply {
            isCorrectRadioGroup.setOnCheckedChangeListener { _, checkedId ->
                TransitionManager.beginDelayedTransition(root as ViewGroup)
                val answer = if (checkedId == R.id.incorrectRadioBtn) {
                    showAnswer(readOnly)

                    val correctAnswer = this.answerEditText.text?.trim()?.toString() ?: ""
                    CorrectnessAnswer(answer = false, correctAnswer = correctAnswer)
                } else {
                    hideAnswer()
                    CorrectnessAnswer(answer = false)
                }

                onQuestionAnswerUpdated?.invoke(answer)
            }

            bind(answerableQuestion, readOnly)
        }
    }

    private fun hideAnswer() {
        binding.apply {
            answerText.hide()
            answerLayout.hide()
        }
    }

    private fun showAnswer(readOnly: Boolean) {
        hideAnswer()
        if (readOnly) {
            binding.answerText.show()
        } else {
            binding.answerLayout.show()
        }
    }

    private fun bind(answerableQuestion: CorrectnessAnswerableQuestion, readOnly: Boolean) {
        binding.apply {
            questionModel = answerableQuestion
            this.readOnly = readOnly
            executePendingBindings()
        }
    }

    companion object {
        fun create(parent: ViewGroup) = CorrectnessQuestionViewHolder(
            PageQuestionCorrectnessBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}