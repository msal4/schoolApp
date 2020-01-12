package com.smart.resources.schools_app.features.exam

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.smart.resources.schools_app.databinding.ItemAddMarkBinding
import com.smart.resources.schools_app.databinding.ItemExamBinding

class AddMarkRecyclerAdapter(private val exams: List<ExamModel>) : RecyclerView.Adapter<AddMarkRecyclerAdapter.MyViewHolder>() {

    inner class MyViewHolder(private val binding: ItemAddMarkBinding) : RecyclerView.ViewHolder(binding.root) {


        /*fun bind(examModel: ExamModel){
            binding.itemModel=examModel
        }*/
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val inflater= LayoutInflater.from(parent.context)
        val binding= ItemAddMarkBinding.inflate(inflater, parent, false)

        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int = 25

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        //holder.bind(exams[position])
    }

}