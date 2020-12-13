package com.smart.resources.schools_app.features.lecture

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.haytham.coder.extensions.isNotNullOrBlank
import com.smart.resources.schools_app.databinding.ItemLectureBinding
import kotlinx.android.synthetic.main.item_lecture.view.*

class LectureRecyclerAdapter: ListAdapter<LectureModel, LectureRecyclerAdapter.MyViewHolder>(DIFF_CALLBACK) {

    var onItemClick: ((LectureModel) -> Unit)?= null
    var onItemPdfClick: ((LectureModel) -> Unit)?= null

    class MyViewHolder(private val binding: ItemLectureBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(lectureModel: LectureModel) {
            binding.model = lectureModel

            binding.hasPdf = lectureModel.pdfURL.isNotNullOrBlank()
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
        holder.itemView.viewPdf.setOnClickListener { onItemPdfClick?.invoke(lectureModel)}
        holder.bind(lectureModel)
    }

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<LectureModel> =
            object : DiffUtil.ItemCallback<LectureModel>() {
                override fun areItemsTheSame(
                    oldItem: LectureModel,
                    newItem: LectureModel
                ): Boolean {
                    return oldItem.subjectID == newItem.subjectID
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