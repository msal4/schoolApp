package com.smart.resources.schools_app.features.absence

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.smart.resources.schools_app.databinding.ItemAbsenceBinding

class AbsenceRecyclerAdapter(private val it: List<StudentAbsenceModel>) : RecyclerView.Adapter<AbsenceRecyclerAdapter.MyViewHolder>() {

    inner class MyViewHolder(private val binding: ItemAbsenceBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(studentAbsenceModel: StudentAbsenceModel){
            binding.itemModel=studentAbsenceModel
        }


    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val inflater= LayoutInflater.from(parent.context)
        val binding= ItemAbsenceBinding.inflate(inflater, parent, false)

        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int = it.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(it[position])
    }

}