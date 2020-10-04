package com.smart.resources.schools_app.features.onlineExam.presentation.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.databinding.ItemStudentBinding
import com.smart.resources.schools_app.features.students.Student

class StudentsQuickAdapter : BaseQuickAdapter<Student, BaseDataBindingHolder<ItemStudentBinding>>(R.layout.item_student) {

    override fun convert(holder: BaseDataBindingHolder<ItemStudentBinding>, item: Student) {
        holder.dataBinding?.apply {
            studentName.text = item.name
            executePendingBindings()
        }
    }
}

