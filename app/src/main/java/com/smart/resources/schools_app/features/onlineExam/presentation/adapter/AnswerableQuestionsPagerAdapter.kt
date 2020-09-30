package com.smart.resources.schools_app.features.onlineExam.presentation.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.smart.resources.schools_app.features.onlineExam.domain.model.*
import com.smart.resources.schools_app.features.onlineExam.presentation.viewHolder.CorrectnessQuestionViewHolder
import com.smart.resources.schools_app.features.onlineExam.presentation.viewHolder.MultiChoiceQuestionViewHolder
import com.smart.resources.schools_app.features.onlineExam.presentation.viewHolder.QuestionViewHolder

class AnswerableQuestionsPagerAdapter(private val readOnly:Boolean) :
    ListAdapter<BaseAnswerableQuestion<out Any>, RecyclerView.ViewHolder>(DIFF_UTIL) {

    companion object {
        private const val QUESTION_ITEM_TYPE = 0
        private const val MULTI_CHOICE_QUESTION_ITEM_TYPE = 1
        private const val CORRECTNESS_QUESTION_ITEM_TYPE = 2

        val DIFF_UTIL = object : DiffUtil.ItemCallback<BaseAnswerableQuestion<out Any>>() {
            override fun areItemsTheSame(
                oldItem: BaseAnswerableQuestion<out Any>,
                newItem: BaseAnswerableQuestion<out Any>
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: BaseAnswerableQuestion<out Any>,
                newItem: BaseAnswerableQuestion<out Any>
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    interface Listener {
        fun onQuestionAnswerStateUpdated(answer: BaseAnswer<out Any>, position: Int)

//        fun onQuestionAnswered(index: Int, answer: String)
//        fun onMultiChoiceQuestionAnswered(index: Int, answer: Int)
//        fun onCorrectnessQuestionAnswered(index: Int, answer: Boolean, correctAnswer: String)
    }

    var listener: Listener? = null


    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        when (val answerableQuestion = getItem(position)) {
            is AnswerableQuestion -> if (holder is QuestionViewHolder) {
                holder.apply {
                    setup(answerableQuestion)
                    onQuestionAnswerStateUpdated =
                        { listener?.onQuestionAnswerStateUpdated(it, position) }
                }
            }
            is MultiChoiceAnswerableQuestion -> if (holder is MultiChoiceQuestionViewHolder) {
                holder.apply {
                        setup(answerableQuestion)
                        onQuestionAnswerUpdated =
                            { listener?.onQuestionAnswerStateUpdated(it, position) }
                }
            }
            is CorrectnessAnswerableQuestion -> if (holder is CorrectnessQuestionViewHolder) {
                holder.apply {
                        setup(answerableQuestion)
                        onQuestionAnswerUpdated =
                            { listener?.onQuestionAnswerStateUpdated(it, position) }
                    }
            }
        }
    }

    override fun getItemViewType(position: Int) = when (getItem(position)) {
        is MultiChoiceAnswerableQuestion -> MULTI_CHOICE_QUESTION_ITEM_TYPE
        is CorrectnessAnswerableQuestion -> CORRECTNESS_QUESTION_ITEM_TYPE
        else -> QUESTION_ITEM_TYPE
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = when (viewType) {
        MULTI_CHOICE_QUESTION_ITEM_TYPE -> MultiChoiceQuestionViewHolder.create(parent, readOnly)
        CORRECTNESS_QUESTION_ITEM_TYPE -> CorrectnessQuestionViewHolder.create(parent, readOnly)
        else -> QuestionViewHolder.create(parent, readOnly)
    }

}