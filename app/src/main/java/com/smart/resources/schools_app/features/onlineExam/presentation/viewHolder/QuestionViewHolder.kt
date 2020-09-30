package com.smart.resources.schools_app.features.onlineExam.presentation.viewHolder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.smart.resources.schools_app.databinding.PageQuestionBinding
import com.smart.resources.schools_app.features.onlineExam.domain.model.Answer
import com.smart.resources.schools_app.features.onlineExam.domain.model.AnswerableQuestion

class QuestionViewHolder(
    var binding: PageQuestionBinding,
    private val readOnly: Boolean
) :
    RecyclerView.ViewHolder(binding.root) {
    var onQuestionAnswerStateUpdated: ((answer: Answer) -> Unit)? = null

    fun setup(answerableQuestion: AnswerableQuestion) {
        binding.answerEditText.addTextChangedListener {
            val answerText = it?.trim()?.toString()

            onQuestionAnswerStateUpdated?.invoke(Answer(answerText ?: ""))
        }

        bind(answerableQuestion)
    }

    private fun bind(answerableQuestion: AnswerableQuestion) {
        binding.apply {
            this.answerableQuestion = answerableQuestion
            readOnly = this@QuestionViewHolder.readOnly
            executePendingBindings()
        }
    }

    companion object {
        fun create(parent: ViewGroup, readOnly: Boolean) = QuestionViewHolder(
            PageQuestionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            readOnly
        )
    }
}