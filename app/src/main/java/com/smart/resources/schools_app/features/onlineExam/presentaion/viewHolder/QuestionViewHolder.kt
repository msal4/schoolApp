package com.smart.resources.schools_app.features.onlineExam.presentaion.viewHolder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.smart.resources.schools_app.databinding.PageQuestionBinding
import com.smart.resources.schools_app.features.onlineExam.domain.model.questions.Question

class QuestionViewHolder(var binding: PageQuestionBinding) :
    RecyclerView.ViewHolder(binding.root) {
    var onQuestionAnswerStateUpdated: (() -> Unit)? = null
    private var oldIsAnswered= false

    fun setup(question: Question){
        binding.answer.addTextChangedListener {
            val answer= it?.trim()?.toString()

            if(answer != null){
                question.answer= answer

                if(question.isAnswered != oldIsAnswered) {
                    onQuestionAnswerStateUpdated?.invoke()
                    oldIsAnswered= question.isAnswered
                }
            }
        }

        bind(question)
    }

    private fun bind(question: Question) {
        binding.apply {
            questionModel = question
            executePendingBindings()
        }
    }

    companion object {
        fun create(parent: ViewGroup) = QuestionViewHolder(
            PageQuestionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
        )
    }
}