package com.smart.resources.schools_app.features.onlineExam.presentation.adapter

import android.view.LayoutInflater
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.databinding.ItemQuestionBinding
import com.smart.resources.schools_app.databinding.ItemQuestionsHeaderBinding
import com.smart.resources.schools_app.features.onlineExam.domain.model.Question
import com.smart.resources.schools_app.features.onlineExam.domain.model.QuestionType

class QuestionsQuickAdapter : BaseQuickAdapter<Question, BaseDataBindingHolder<ItemQuestionBinding>>(R.layout.item_question) {


    init {
        setDiffCallback(QuestionDiffCallback())
    }

    private lateinit var headerBinding: ItemQuestionsHeaderBinding

    private fun updateHeaderView(list: List<Question>) {
        headerBinding.apply {
            val questionTypeGroups = list.groupBy { it.questionType }
            QuestionType.values().forEach {
                when (it) {
                    QuestionType.NORMAL -> normalQuestionsCount =
                        questionTypeGroups[it]?.count() ?: 0
                    QuestionType.CORRECTNESS -> correctnessQuestionsCount =
                        questionTypeGroups[it]?.count() ?: 0
                    QuestionType.MULTI_CHOICE -> multiChoiceQuestionsCount =
                        questionTypeGroups[it]?.count() ?: 0
                }
            }
            executePendingBindings()
        }
    }

    override fun setDiffNewData(list: MutableList<Question>?) {
        super.setDiffNewData(list)
        updateHeaderView(list?: emptyList())
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        headerBinding = ItemQuestionsHeaderBinding.inflate(LayoutInflater.from(context))
        setHeaderView(headerBinding.root)
    }

    override fun convert(holder: BaseDataBindingHolder<ItemQuestionBinding>, item: Question) {
        holder.dataBinding?.apply {
            model = item
            executePendingBindings()
        }
    }
}

class QuestionDiffCallback : DiffUtil.ItemCallback<Question>() {

    override fun areItemsTheSame(
        oldItem: Question,
        newItem: Question
    ) = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: Question,
        newItem: Question
    ) = oldItem == newItem


    override fun getChangePayload(
        oldItem: Question,
        newItem: Question
    ): Any? {
        return null
    }
}