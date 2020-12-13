package com.smart.resources.schools_app.features.subject

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.smart.resources.schools_app.databinding.ItemSubjectBinding

class SubjectRecyclerAdapter: ListAdapter<Subject, SubjectRecyclerAdapter.MyViewHolder>(DIFF_CALLBACK) {

    var onItemClick: ((Subject) -> Unit)?= null

    class MyViewHolder(private val binding: ItemSubjectBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(subject: Subject) {
            binding.model = subject
        }

        companion object {
            fun create(parent: ViewGroup) = MyViewHolder(
                ItemSubjectBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun submitList(list: List<Subject>?) {
        super.submitList(list?.toList())
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = MyViewHolder.create(parent)


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val lectureModel = getItem(position)

        holder.itemView.setOnClickListener { onItemClick?.invoke(lectureModel) }
        holder.bind(lectureModel)
    }

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<Subject> =
            object : DiffUtil.ItemCallback<Subject>() {
                override fun areItemsTheSame(
                    oldItem: Subject,
                    newItem: Subject
                ): Boolean {
                    return oldItem.idSubject == newItem.idSubject
                }

                override fun areContentsTheSame(
                    oldItem: Subject,
                    newItem: Subject
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}