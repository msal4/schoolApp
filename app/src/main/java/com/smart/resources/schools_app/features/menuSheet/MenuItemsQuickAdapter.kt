package com.smart.resources.schools_app.features.menuSheet

import android.annotation.SuppressLint
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.databinding.ItemMenuBinding

class MenuItemsQuickAdapter : BaseQuickAdapter<MenuItemData, BaseDataBindingHolder<ItemMenuBinding>>(R.layout.item_menu) {

    override fun convert(holder: BaseDataBindingHolder<ItemMenuBinding>, item: MenuItemData) {
        holder.dataBinding?.apply {
            menuItem= item
            executePendingBindings()
        }
    }
}

