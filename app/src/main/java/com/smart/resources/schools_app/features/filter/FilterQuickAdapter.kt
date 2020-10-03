package com.smart.resources.schools_app.features.filter

import android.view.LayoutInflater
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.bindingAdapters.setVisible
import com.smart.resources.schools_app.core.typeConverters.room.QuestionType
import com.smart.resources.schools_app.databinding.ItemFilterBinding
import com.smart.resources.schools_app.databinding.ItemQuestionBinding
import com.smart.resources.schools_app.databinding.ItemQuestionsHeaderBinding
import com.smart.resources.schools_app.features.onlineExam.domain.model.Question

class FilterQuickAdapter : BaseQuickAdapter<FilterItem, BaseDataBindingHolder<ItemFilterBinding>>(R.layout.item_filter) {

    override fun convert(holder: BaseDataBindingHolder<ItemFilterBinding>, item: FilterItem) {
        holder.dataBinding?.apply {
            labelText.text= item.item
            checkIcon.setVisible(item.selected)
        }
    }
}
