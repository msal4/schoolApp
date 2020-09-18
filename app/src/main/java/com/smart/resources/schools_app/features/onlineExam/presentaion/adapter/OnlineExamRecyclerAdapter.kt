package com.smart.resources.schools_app.features.onlineExam.presentaion.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.smart.resources.schools_app.databinding.ItemOnlineExamBinding
import com.smart.resources.schools_app.features.onlineExam.domain.model.OnlineExamItem

class OnlineExamRecyclerAdapter :
    ListAdapter<OnlineExamItem, OnlineExamRecyclerAdapter.MyViewHolder>(DIFF_CALLBACK) {

    var onItemPressed: (() -> Unit)? = null


    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ) {
        val model = getItem(position)
        holder.apply {
            bind(model)
            binding.root.setOnClickListener { onItemPressed?.invoke() }
        }

    }

    override fun submitList(list: List<OnlineExamItem>?) {
        super.submitList(list?.toList())
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = MyViewHolder.create(parent)

    class MyViewHolder(var binding: ItemOnlineExamBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: OnlineExamItem) {
            binding.onlineExamItem = model
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
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<OnlineExamItem> =
            object : DiffUtil.ItemCallback<OnlineExamItem>() {
                override fun areItemsTheSame(
                    oldItem: OnlineExamItem,
                    newItem: OnlineExamItem
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: OnlineExamItem,
                    newItem: OnlineExamItem
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}