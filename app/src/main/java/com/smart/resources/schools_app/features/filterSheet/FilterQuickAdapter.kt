package com.smart.resources.schools_app.features.filterSheet

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.bindingAdapters.setVisible
import com.smart.resources.schools_app.databinding.ItemFilterBinding

class FilterQuickAdapter : BaseQuickAdapter<FilterItem, BaseDataBindingHolder<ItemFilterBinding>>(R.layout.item_filter) {

    override fun convert(holder: BaseDataBindingHolder<ItemFilterBinding>, item: FilterItem) {
        holder.dataBinding?.apply {
            labelText.text= item.item
            checkIcon.setVisible(item.selected)
        }
    }
}
