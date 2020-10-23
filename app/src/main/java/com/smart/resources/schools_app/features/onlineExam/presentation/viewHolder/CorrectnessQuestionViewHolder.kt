package com.smart.resources.schools_app.features.onlineExam.presentation.viewHolder

import android.text.Editable
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.haytham.coder.extensions.hide
import com.haytham.coder.extensions.show
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.extentions.debounce
import com.smart.resources.schools_app.databinding.PageQuestionCorrectnessBinding
import com.smart.resources.schools_app.features.onlineExam.domain.model.Answer
import com.smart.resources.schools_app.features.onlineExam.domain.model.CorrectnessAnswer
import com.smart.resources.schools_app.features.onlineExam.domain.model.CorrectnessAnswerableQuestion
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class CorrectnessQuestionViewHolder(
    val binding: PageQuestionCorrectnessBinding
) : RecyclerView.ViewHolder(binding.root) {
    var onQuestionAnswerUpdated: ((correctnessAnswer: CorrectnessAnswer) -> Unit)? = null

    fun setup(answerableQuestion: CorrectnessAnswerableQuestion, readOnly: Boolean) {
        binding.apply {
            if(answerableQuestion.answer?.hasCorrectAnswer== true){
                showAnswer(readOnly)
            }
            listenToSelectionChange(readOnly)
            listenToCorrectAnswerChange()

            bind(answerableQuestion, readOnly)
        }
    }

    private fun listenToCorrectAnswerChange() {
        val afterTextChanged: (Editable?) -> Unit = debounce(
            coroutineScope = CoroutineScope(Dispatchers.IO),
            destinationFunction = {
                val correctAnswer = it?.trim()?.toString().toString()

                val answer = CorrectnessAnswer(
                    answer = binding.incorrectRadioBtn.text.trim().toString(),
                    correctnessAnswer = false,
                    correctAnswer = correctAnswer,
                )

                onQuestionAnswerUpdated?.invoke(answer)
            }
        )
        binding.answerEditText.addTextChangedListener(afterTextChanged = afterTextChanged)
    }

    private fun PageQuestionCorrectnessBinding.listenToSelectionChange(
        readOnly: Boolean
    ) {
        isCorrectRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            TransitionManager.beginDelayedTransition(root as ViewGroup)
            val answerText =
                isCorrectRadioGroup.findViewById<RadioButton?>(checkedId)?.text?.trim().toString()

            val answer = if (checkedId == R.id.incorrectRadioBtn) {
                showAnswer(readOnly)

                val correctAnswer = this.answerEditText.text?.trim()?.toString() ?: ""
                CorrectnessAnswer(
                    answer = answerText,
                    correctnessAnswer = false,
                    correctAnswer = correctAnswer,
                )
            } else {
                hideAnswer()
                CorrectnessAnswer(answer = answerText, correctnessAnswer = true)
            }

            onQuestionAnswerUpdated?.invoke(answer)
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