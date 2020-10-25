package com.smart.resources.schools_app.features.addMark

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.smart.resources.schools_app.databinding.ItemAddMarkBinding
import com.smart.resources.schools_app.features.students.models.Marks
import com.smart.resources.schools_app.features.students.models.StudentWithMark
import java.util.*

class AddMarkRecyclerAdapter(private val exams: List<StudentWithMark>) :
    RecyclerView.Adapter<AddMarkRecyclerAdapter.MyViewHolder>() {

    companion object {
        val sendStudentResult: ArrayList<Marks> = ArrayList()
    }


    inner class MyViewHolder(val binding: ItemAddMarkBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(examModel: StudentWithMark) {
            binding.itemModel = examModel
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemAddMarkBinding.inflate(inflater, parent, false)

        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int = exams.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(exams[position])

        val studentId = exams[position].id

        holder.binding.apply {
            mark.doOnTextChanged { text, _, _, _ ->
                if(text.isNullOrBlank()) return@doOnTextChanged
                else if (text.toString().toInt() > 100 || text.toString().toInt() < 0) {
                    mark.error = "خطأ في الادخال"
                }
                else {
                    sendStudentResult.add(
                        Marks(
                            text.toString().toInt(),
                            studentId
                        )
                    )
                }
            }
        }

    }
}