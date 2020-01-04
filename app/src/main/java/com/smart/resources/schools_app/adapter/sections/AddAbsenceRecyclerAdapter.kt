package com.smart.resources.schools_app.adapter.sections

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.smart.resources.schools_app.database.model.ExamModel
import com.smart.resources.schools_app.database.model.StudentAbsenceModel
import com.smart.resources.schools_app.databinding.ItemAbsenceBinding
import com.smart.resources.schools_app.databinding.ItemAddAbsenceBinding

class AddAbsenceRecyclerAdapter(private val it: List<StudentAbsenceModel>) : RecyclerView.Adapter<AddAbsenceRecyclerAdapter.MyViewHolder>() {

    inner class MyViewHolder(private val binding: ItemAddAbsenceBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(studentAbsenceModel: StudentAbsenceModel){
            binding.itemModel=studentAbsenceModel
        }


    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val inflater= LayoutInflater.from(parent.context)
        val binding= ItemAddAbsenceBinding.inflate(inflater, parent, false)

        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int = it.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(it[position])
    }

}