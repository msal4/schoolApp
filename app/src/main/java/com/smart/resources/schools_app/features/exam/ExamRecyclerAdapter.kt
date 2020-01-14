package com.smart.resources.schools_app.features.exam

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.smart.resources.schools_app.databinding.ItemExamBinding

class ExamRecyclerAdapter(private val exams: List<ExamModel>,
                          private val listener:OnItemClickListener) : RecyclerView.Adapter<ExamRecyclerAdapter.MyViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(examModel: ExamModel)
    }

    inner class MyViewHolder(private val binding: ItemExamBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(examModel: ExamModel){
            binding.itemModel=examModel
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val inflater= LayoutInflater.from(parent.context)
        val binding= ItemExamBinding.inflate(inflater, parent, false)

        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int = exams.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val exam= exams[position]
        holder.itemView.setOnClickListener { listener.onItemClick(exam) }
        holder.bind(exam)
    }

}