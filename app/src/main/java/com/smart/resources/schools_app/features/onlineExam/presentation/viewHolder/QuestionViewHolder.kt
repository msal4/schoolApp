package com.smart.resources.schools_app.features.onlineExam.presentation.viewHolder

import android.text.Editable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.findFragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.smart.resources.schools_app.core.extentions.debounce
import com.smart.resources.schools_app.databinding.PageQuestionBinding
import com.smart.resources.schools_app.features.onlineExam.domain.model.Answer
import com.smart.resources.schools_app.features.onlineExam.domain.model.AnswerableQuestion
import com.smart.resources.schools_app.features.onlineExam.presentation.fragments.ExamPaperFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO

// TODO: fix fields refreshes when typing answer
class QuestionViewHolder(
    var binding: PageQuestionBinding) :
    RecyclerView.ViewHolder(binding.root) {
    var onQuestionAnswerStateUpdated: ((answer: Answer) -> Unit)? = null

    fun setup(answerableQuestion: AnswerableQuestion, readOnly: Boolean) {
        val afterTextChanged:(Editable?)->Unit= debounce(
            coroutineScope = CoroutineScope(IO),
            destinationFunction = {
                val answerText = it?.trim()?.toString()
                onQuestionAnswerStateUpdated?.invoke(Answer(answerText ?: ""))
            }
        )

        binding.answerEditText.addTextChangedListener(afterTextChanged = afterTextChanged)
        bind(answerableQuestion, readOnly)
    }

    private fun bind(answerableQuestion: AnswerableQuestion, readOnly: Boolean) {
        binding.apply {
            this.answerableQuestion = answerableQuestion
            this.readOnly = readOnly
            executePendingBindings()
        }
    }

    companion object {
        fun create(parent: ViewGroup) = QuestionViewHolder(
            PageQuestionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}