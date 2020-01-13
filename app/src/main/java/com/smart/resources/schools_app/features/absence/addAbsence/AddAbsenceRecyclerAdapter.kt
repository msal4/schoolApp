package com.smart.resources.schools_app.features.absence.addAbsence

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.smart.resources.schools_app.databinding.ItemAddAbsenceBinding
import com.smart.resources.schools_app.features.absence.AddAbsenceModel
import com.smart.resources.schools_app.features.students.Student

class AddAbsenceRecyclerAdapter()
    : RecyclerView.Adapter<AddAbsenceRecyclerAdapter.MyViewHolder>() {
    private var students: List<AddAbsenceModel>?= null

    fun updateData(students: List<AddAbsenceModel>){
        this.students = students
        notifyDataSetChanged()
    }

    fun clearData(){
        students= null
        notifyDataSetChanged()
    }

    fun uncheckAll(){
        students?.apply {
            forEach { it.isChecked= false }
            notifyDataSetChanged()
        }
    }


    inner class MyViewHolder(val binding: ItemAddAbsenceBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val inflater= LayoutInflater.from(parent.context)
        val binding= ItemAddAbsenceBinding.inflate(inflater, parent, false)

        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int = students?.size?:0

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if(students.isNullOrEmpty()) return
        val student= students!![position]


        holder.binding.apply {
            studentCheckBox.text= student.name
            studentCheckBox.isChecked= student.isChecked
            studentCheckBox.setOnCheckedChangeListener { _, isChecked ->
                student.isChecked= isChecked
            }
        }
    }

}