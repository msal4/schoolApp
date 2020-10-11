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

class QuestionViewHolder(
    var binding: PageQuestionBinding,
    private val readOnly: Boolean
) :
    RecyclerView.ViewHolder(binding.root) {
    var onQuestionAnswerStateUpdated: ((answer: Answer) -> Unit)? = null

    fun setup(answerableQuestion: AnswerableQuestion) {
        val afterTextChanged:(Editable?)->Unit= debounce(
            coroutineScope = CoroutineScope(IO),
            destinationFunction = {
                val answerText = it?.trim()?.toString()
                onQuestionAnswerStateUpdated?.invoke(Answer(answerText ?: ""))
            }
        )

        binding.answerEditText.addTextChangedListener(afterTextChanged = afterTextChanged)
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