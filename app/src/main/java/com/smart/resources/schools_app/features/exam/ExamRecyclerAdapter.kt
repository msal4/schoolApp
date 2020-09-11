package com.smart.resources.schools_app.features.exam

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.smart.resources.schools_app.databinding.ItemExamBinding
import com.smart.resources.schools_app.features.users.UsersRepository

class ExamRecyclerAdapter(private val listener:OnItemClickListener
) : ListAdapter<ExamModel, ExamRecyclerAdapter.MyViewHolder>(DIFF_CALLBACK) {
    private val isStudent= UsersRepository.instance.getCurrentUserAccount()?.userType==0

    interface OnItemClickListener {
        fun onItemClick(examModel: ExamModel)
    }

    inner class MyViewHolder(private val binding: ItemExamBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(examModel: ExamModel){
            binding.itemModel=examModel
        }
    }

    override fun submitList(list: List<ExamModel>?) {
        super.submitList(list?.toList())
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val inflater= LayoutInflater.from(parent.context)
        val binding= ItemExamBinding.inflate(inflater, parent, false)

        return MyViewHolder(binding)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val exam= getItem(position)

        if(!isStudent)holder.itemView.setOnClickListener { listener.onItemClick(exam) }
        holder.bind(exam)
    }

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<ExamModel> =
            object : DiffUtil.ItemCallback<ExamModel>() {
                override fun areItemsTheSame(
                    oldItem: ExamModel,
                    newItem: ExamModel
                ): Boolean {
                    return oldItem.idExam == newItem.idExam
                }

                override fun areContentsTheSame(
                    oldItem: ExamModel,
                    newItem: ExamModel
                ): Boolean {
                    return oldItem.isContentSame(newItem)
                }
            }
    }
}