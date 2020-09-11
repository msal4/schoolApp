package com.smart.resources.schools_app.features.lecture

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.smart.resources.schools_app.databinding.ItemLectureBinding

class LectureRecyclerAdapter: ListAdapter<LectureModel, LectureRecyclerAdapter.MyViewHolder>(DIFF_CALLBACK) {

    var onItemClick: ((LectureModel) -> Unit)?= null

    class MyViewHolder(private val binding: ItemLectureBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(lectureModel: LectureModel) {
            binding.model = lectureModel
        }

        companion object {
            fun create(parent: ViewGroup) = MyViewHolder(
                ItemLectureBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun submitList(list: List<LectureModel>?) {
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
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<LectureModel> =
            object : DiffUtil.ItemCallback<LectureModel>() {
                override fun areItemsTheSame(
                    oldItem: LectureModel,
                    newItem: LectureModel
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: LectureModel,
                    newItem: LectureModel
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}