package com.smart.resources.schools_app.features.onlineExam.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.smart.resources.schools_app.databinding.ItemOnlineExamBinding
import com.smart.resources.schools_app.features.onlineExam.domain.model.OnlineExamDetails

class OnlineExamAdapter(private val isStudent:Boolean) :
    ListAdapter<OnlineExamDetails, OnlineExamAdapter.MyViewHolder>(DIFF_CALLBACK) {

    var onItemPressed: ((onlineExamDetails:OnlineExamDetails) -> Unit)? = null



    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ) {
        val model = getItem(position)
        holder.apply {
            bind(model, isStudent)
            binding.root.setOnClickListener { onItemPressed?.invoke(model) }
        }

    }

    override fun submitList(list: List<OnlineExamDetails>?) {
        super.submitList(list?.toList())
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = MyViewHolder.create(parent)

    class MyViewHolder(var binding: ItemOnlineExamBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: OnlineExamDetails, isStudent: Boolean) {
            binding.apply {
                onlineExamItem = model
                this.isStudent= isStudent
                executePendingBindings()
            }
        }

        companion object {
            fun create(parent: ViewGroup) = MyViewHolder(
                ItemOnlineExamBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
            )
        }
    }


    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<OnlineExamDetails> =
            object : DiffUtil.ItemCallback<OnlineExamDetails>() {
                override fun areItemsTheSame(
                    oldDetails: OnlineExamDetails,
                    newDetails: OnlineExamDetails
                ): Boolean {
                    return oldDetails.id == newDetails.id
                }

                override fun areContentsTheSame(
                    oldDetails: OnlineExamDetails,
                    newDetails: OnlineExamDetails
                ): Boolean {
                    return oldDetails == newDetails
                }
            }
    }
}