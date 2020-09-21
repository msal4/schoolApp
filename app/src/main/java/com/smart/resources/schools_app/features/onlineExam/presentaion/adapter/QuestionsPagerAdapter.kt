package com.smart.resources.schools_app.features.onlineExam.presentaion.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.smart.resources.schools_app.features.onlineExam.domain.model.questions.BaseQuestion
import com.smart.resources.schools_app.features.onlineExam.domain.model.questions.CorrectnessQuestion
import com.smart.resources.schools_app.features.onlineExam.domain.model.questions.MultiChoiceQuestion
import com.smart.resources.schools_app.features.onlineExam.domain.model.questions.Question
import com.smart.resources.schools_app.features.onlineExam.presentaion.viewHolder.CorrectnessQuestionViewHolder
import com.smart.resources.schools_app.features.onlineExam.presentaion.viewHolder.MultiChoiceQuestionViewHolder
import com.smart.resources.schools_app.features.onlineExam.presentaion.viewHolder.QuestionViewHolder

class QuestionsPagerAdapter :
    ListAdapter<BaseQuestion<out Any?>, RecyclerView.ViewHolder>(DIFF_UTIL) {

    companion object {
        private const val QUESTION_ITEM_TYPE = 0
        private const val MULTI_CHOICE_QUESTION_ITEM_TYPE = 1
        private const val CORRECTNESS_QUESTION_ITEM_TYPE = 2

        val DIFF_UTIL = object : DiffUtil.ItemCallback<BaseQuestion<out Any?>>() {
            override fun areItemsTheSame(
                oldItem: BaseQuestion<out Any?>,
                newItem: BaseQuestion<out Any?>
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: BaseQuestion<out Any?>,
                newItem: BaseQuestion<out Any?>
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    interface Listener {
        fun onQuestionAnswerStateUpdated()

//        fun onQuestionAnswered(index: Int, answer: String)
//        fun onMultiChoiceQuestionAnswered(index: Int, answer: Int)
//        fun onCorrectnessQuestionAnswered(index: Int, answer: Boolean, correctAnswer: String)
    }

    var listener: Listener? = null


    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        val questionModel = getItem(position)
        when (holder) {
            is QuestionViewHolder -> holder.apply {
                if (questionModel is Question) {
                    setup(questionModel)
                    onQuestionAnswerStateUpdated = {listener?.onQuestionAnswerStateUpdated()}
                }
            }
            is MultiChoiceQuestionViewHolder -> holder.apply {
                if (questionModel is MultiChoiceQuestion) {
                    setup(questionModel)
                    onQuestionAnswerStateUpdated = {listener?.onQuestionAnswerStateUpdated()}
                }
            }
            is CorrectnessQuestionViewHolder -> holder.apply {
                if (questionModel is CorrectnessQuestion) {
                    setup(questionModel)
                    onQuestionAnswerStateUpdated = {listener?.onQuestionAnswerStateUpdated()}
                }
            }
        }
    }


    override fun getItemViewType(position: Int) = when (getItem(position)) {
        is MultiChoiceQuestion -> MULTI_CHOICE_QUESTION_ITEM_TYPE
        is CorrectnessQuestion -> CORRECTNESS_QUESTION_ITEM_TYPE
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