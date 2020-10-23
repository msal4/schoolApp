package com.smart.resources.schools_app.features.onlineExam.presentation.adapter

import android.annotation.SuppressLint
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.haytham.coder.extensions.unicodeWrap
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.databinding.ItemStudentBinding
import com.smart.resources.schools_app.features.students.models.Student
import com.smart.resources.schools_app.features.students.models.StudentWithAnswerStatus

class StudentsQuickAdapter : BaseQuickAdapter<StudentWithAnswerStatus, BaseDataBindingHolder<ItemStudentBinding>>(R.layout.item_student) {

    override fun convert(holder: BaseDataBindingHolder<ItemStudentBinding>, item: StudentWithAnswerStatus) {
        holder.dataBinding?.apply {
            orderText.text= String.format("-%d", holder.adapterPosition +1)
            student= item
            executePendingBindings()
        }
    }
}

