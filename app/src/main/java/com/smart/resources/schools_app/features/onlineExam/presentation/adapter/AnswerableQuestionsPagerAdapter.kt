package com.smart.resources.schools_app.features.onlineExam.presentation.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.smart.resources.schools_app.features.onlineExam.domain.model.*
import com.smart.resources.schools_app.features.onlineExam.presentation.viewHolder.CorrectnessQuestionViewHolder
import com.smart.resources.schools_app.features.onlineExam.presentation.viewHolder.MultiChoiceQuestionViewHolder
import com.smart.resources.schools_app.features.onlineExam.presentation.viewHolder.QuestionViewHolder

class AnswerableQuestionsPagerAdapter :
    ListAdapter<BaseAnswerableQuestion<Any>, RecyclerView.ViewHolder>(DIFF_UTIL) {
    private var readOnly:Boolean= true

    companion object {
        private const val QUESTION_ITEM_TYPE = 0
        private const val MULTI_CHOICE_QUESTION_ITEM_TYPE = 1
        private const val CORRECTNESS_QUESTION_ITEM_TYPE = 2

        val DIFF_UTIL = object : DiffUtil.ItemCallback<BaseAnswerableQuestion<out Any>>() {
            override fun areItemsTheSame(
                oldItem: BaseAnswerableQuestion<Any>,
                newItem: BaseAnswerableQuestion<Any>
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: BaseAnswerableQuestion<Any>,
                newItem: BaseAnswerableQuestion<Any>
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    interface Listener {
        fun onQuestionAnswerStateUpdated(answer: BaseAnswer<Any>, position: Int)
    }

    var listener: Listener? = null


    fun updateReadOnly(readOnly: Boolean){
        this.readOnly= readOnly
        notifyDataSetChanged()
    }
    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        when (val answerableQuestion = getItem(position)) {
            is AnswerableQuestion -> if (holder is QuestionViewHolder) {
                holder.apply {
                    setup(answerableQuestion, readOnly)
                    onQuestionAnswerStateUpdated =
                        { listener?.onQuestionAnswerStateUpdated(it, position) }
                }
            }
            is MultiChoiceAnswerableQuestion -> if (holder is MultiChoiceQuestionViewHolder) {
                holder.apply {
                        setup(answerableQuestion, readOnly)
                        onQuestionAnswerUpdated =
                            { listener?.onQuestionAnswerStateUpdated(it, position) }
                }
            }
            is CorrectnessAnswerableQuestion -> if (holder is CorrectnessQuestionViewHolder) {
                holder.apply {
                        setup(answerableQuestion, readOnly)
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
        MULTI_CHOICE_QUESTION_ITEM_TYPE -> MultiChoiceQuestionViewHolder.create(parent)
        CORRECTNESS_QUESTION_ITEM_TYPE -> CorrectnessQuestionViewHolder.create(parent)
        else -> QuestionViewHolder.create(parent)
    }

}