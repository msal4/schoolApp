package com.smart.resources.schools_app.features.onlineExam.presentaion.viewHolder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.databinding.PageQuestionMultiChoiceBinding
import com.smart.resources.schools_app.features.onlineExam.domain.model.questions.MultiChoiceQuestion

class MultiChoiceQuestionViewHolder(var binding: PageQuestionMultiChoiceBinding) :
    RecyclerView.ViewHolder(binding.root) {
    var onQuestionAnswerStateUpdated: (() -> Unit)? = null
    private var oldIsAnswered= false

    fun setup(question: MultiChoiceQuestion) {
        binding.apply {
            choicesRadioGroup.setOnCheckedChangeListener { _, checkedId ->
               question.answer= when(checkedId){
                    R.id.choice1RadioBtn -> 0
                    R.id.choice2RadioBtn -> 1
                    R.id.choice3RadioBtn -> 2
                   else -> -1
                }
                if(question.isAnswered != oldIsAnswered) {
                    onQuestionAnswerStateUpdated?.invoke()
                    oldIsAnswered= question.isAnswered
                }
            }
        }

        bind(question)
    }

    private fun bind(question: MultiChoiceQuestion) {
        binding.apply {
            questionModel = question
            executePendingBindings()
        }
    }

    companion object {
        fun create(parent: ViewGroup) = MultiChoiceQuestionViewHolder(
            PageQuestionMultiChoiceBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
        )
    }
}