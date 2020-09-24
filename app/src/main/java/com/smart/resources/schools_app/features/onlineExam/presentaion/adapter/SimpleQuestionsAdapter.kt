package com.smart.resources.schools_app.features.onlineExam.presentaion.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.smart.resources.schools_app.databinding.ItemSimpleQuestionBinding
import com.smart.resources.schools_app.features.onlineExam.domain.model.Question

class SimpleQuestionsAdapter :
    ListAdapter<Question, SimpleQuestionsAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ) {
        val model = getItem(position)
        holder.apply {
            bind(model)
        }

    }

    override fun submitList(list: List<Question>?) {
        super.submitList(list?.toList())
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = MyViewHolder.create(parent)

    class MyViewHolder(var binding: ItemSimpleQuestionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(question: Question) {
            binding.apply {
                executePendingBindings()
            }
        }

        companion object {
            fun create(parent: ViewGroup) = MyViewHolder(
                ItemSimpleQuestionBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
            )
        }
    }


    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<Question> =
            object : DiffUtil.ItemCallback<Question>() {
                override fun areItemsTheSame(
                    oldDetails: Question,
                    newDetails: Question
                ): Boolean {
                    return oldDetails.id == newDetails.id
                }

                override fun areContentsTheSame(
                    oldDetails: Question,
                    newDetails: Question
                ): Boolean {
                    return oldDetails == newDetails
                }
            }
    }
}