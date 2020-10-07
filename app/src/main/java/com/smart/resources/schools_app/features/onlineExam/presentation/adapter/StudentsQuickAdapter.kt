package com.smart.resources.schools_app.features.onlineExam.presentation.adapter

import android.annotation.SuppressLint
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.haytham.coder.extensions.unicodeWrap
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.databinding.ItemStudentBinding
import com.smart.resources.schools_app.features.students.Student

class StudentsQuickAdapter : BaseQuickAdapter<Student, BaseDataBindingHolder<ItemStudentBinding>>(R.layout.item_student) {

    @SuppressLint("SetTextI18n")
    override fun convert(holder: BaseDataBindingHolder<ItemStudentBinding>, item: Student) {
        holder.dataBinding?.apply {
            studentName.text = item.name
            orderText.text= "${holder.adapterPosition +1}-"
            executePendingBindings()
        }
    }
}

