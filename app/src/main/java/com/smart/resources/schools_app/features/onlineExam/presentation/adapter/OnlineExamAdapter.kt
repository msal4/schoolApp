package com.smart.resources.schools_app.features.onlineExam.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.smart.resources.schools_app.databinding.ItemOnlineExamBinding
import com.smart.resources.schools_app.features.onlineExam.domain.model.onlineExam.OnlineExam

class OnlineExamAdapter(private val isStudent:Boolean) :
    ListAdapter<OnlineExam, OnlineExamAdapter.MyViewHolder>(DIFF_CALLBACK) {

    var onItemPressed: ((onlineExam: OnlineExam) -> Unit)? = null
    var onItemLongPressed: ((onlineExam: OnlineExam) -> Unit)? = null

    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ) {
        val model = getItem(position)
        holder.apply {
            bind(model, isStudent)
            binding.root.apply {
                setOnClickListener { onItemPressed?.invoke(model) }
                setOnLongClickListener {
                    onItemLongPressed?.invoke(model)
                    onItemLongPressed!= null
                }
            }
        }

    }

    override fun submitList(list: List<OnlineExam>?) {
        super.submitList(list?.toList())
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = MyViewHolder.create(parent)

    class MyViewHolder(var binding: ItemOnlineExamBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: OnlineExam, isStudent: Boolean) {
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
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<OnlineExam> =
            object : DiffUtil.ItemCallback<OnlineExam>() {
                override fun areItemsTheSame(
                    old: OnlineExam,
                    aNew: OnlineExam
                ): Boolean {
                    return old.id == aNew.id
                }

                override fun areContentsTheSame(
                    old: OnlineExam,
                    aNew: OnlineExam
                ): Boolean {
                    return old == aNew
                }
            }
    }
}