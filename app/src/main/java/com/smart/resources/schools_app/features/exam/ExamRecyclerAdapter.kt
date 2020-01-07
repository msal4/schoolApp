package com.smart.resources.schools_app.features.exam

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.smart.resources.schools_app.databinding.ItemExamBinding

class ExamRecyclerAdapter(private val body: List<ExamModel>) : RecyclerView.Adapter<ExamRecyclerAdapter.MyViewHolder>() {

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

    override fun getItemCount(): Int = body.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.bind(body[position])
    }

}