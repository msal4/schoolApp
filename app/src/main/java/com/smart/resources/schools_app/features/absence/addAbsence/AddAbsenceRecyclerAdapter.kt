package com.smart.resources.schools_app.features.absence.addAbsence

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.smart.resources.schools_app.databinding.ItemAddAbsenceBinding
import com.smart.resources.schools_app.features.students.Student

class AddAbsenceRecyclerAdapter(val onStudentSelected: (Student, Boolean)->Unit)
    : RecyclerView.Adapter<AddAbsenceRecyclerAdapter.MyViewHolder>() {
    private var students: List<Student> = listOf()

    fun updateData(students: List<Student>){
        this.students = students
        notifyDataSetChanged()
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

    override fun getItemCount(): Int = students.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val student= students[position]
        holder.binding.apply {
            studentCheckBox.text= student.name
            studentCheckBox.setOnCheckedChangeListener { _, isChecked ->
                onStudentSelected(student, isChecked)
            }
        }
    }

}