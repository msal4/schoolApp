package com.smart.resources.schools_app.features.exam

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.smart.resources.schools_app.databinding.ItemAddMarkBinding
import com.smart.resources.schools_app.features.students.Student
import com.smart.resources.schools_app.features.students.Marks
import java.util.*

class AddMarkRecyclerAdapter(private val exams: List<Student>) :
    RecyclerView.Adapter<AddMarkRecyclerAdapter.MyViewHolder>() {

    companion object {
        var sendStudentResult: ArrayList<Marks> = ArrayList()
    }


    inner class MyViewHolder(var binding: ItemAddMarkBinding) :
        RecyclerView.ViewHolder(binding.root) {


        var editText: EditText
        var editText2: TextView

        init {


            editText = binding.mark
            editText2 = binding.stID

            editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    charSequence: CharSequence,
                    i: Int,
                    i1: Int,
                    i2: Int
                ) {

                }

                override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                    if (editText.isEnabled) {

                        var iter = sendStudentResult.iterator()

                        while (iter.hasNext()) {
                            val st: Marks = iter.next()
                            if (st.studentId == editText2.text.toString()) iter.remove()
                        }
                        if (editText.text.toString() != "") {
                            sendStudentResult.add(
                                Marks(
                                    editText.text.toString().toInt(),
                                    editText2.text.toString()
                                )
                            )
                        }
                    }

                }

                override fun afterTextChanged(editable: Editable) {

                    if (editText.text.isNotEmpty())
                        if (editText.text.toString().toInt() > 100 || editText.text.toString().toInt() < 0) {
                            editText.error = "خطأ في الادخال"
                        }

                }
            })

        }


        fun bind(examModel: Student) {
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


    }

}