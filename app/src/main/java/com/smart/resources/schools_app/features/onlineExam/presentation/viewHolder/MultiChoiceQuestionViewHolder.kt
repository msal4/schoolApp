package com.smart.resources.schools_app.features.onlineExam.presentation.viewHolder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.databinding.PageQuestionMultiChoiceBinding
import com.smart.resources.schools_app.features.onlineExam.domain.model.MultiChoiceAnswer
import com.smart.resources.schools_app.features.onlineExam.domain.model.MultiChoiceAnswerableQuestion

class MultiChoiceQuestionViewHolder(
    val binding: PageQuestionMultiChoiceBinding) :
    RecyclerView.ViewHolder(binding.root) {
    var onQuestionAnswerUpdated: ((answer: MultiChoiceAnswer) -> Unit)? = null

    fun setup(answerableQuestion: MultiChoiceAnswerableQuestion, readOnly: Boolean) {
        binding.apply {
            choicesRadioGroup.setOnCheckedChangeListener { _, checkedId ->
                val selection = when (checkedId) {
                    R.id.choice1RadioBtn -> 0
                    R.id.choice2RadioBtn -> 1
                    R.id.choice3RadioBtn -> 2
                    else -> -1

                }
                onQuestionAnswerUpdated?.invoke(MultiChoiceAnswer(selection))
            }
        }

        bind(answerableQuestion, readOnly)
    }

    private fun bind(answerableQuestion: MultiChoiceAnswerableQuestion, readOnly: Boolean) {
        binding.apply {
            this.answerableQuestion = answerableQuestion
            this.readOnly = readOnly
            executePendingBindings()
        }
    }

    companion object {
        fun create(parent: ViewGroup) = MultiChoiceQuestionViewHolder(
            PageQuestionMultiChoiceBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}