package com.smart.resources.schools_app.features.menuSheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.chad.library.adapter.base.BaseQuickAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.typeConverters.room.OnlineExamStatus
import com.smart.resources.schools_app.databinding.BottomSheetRecyclerBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuBottomSheet : BottomSheetDialogFragment() {
    private lateinit var binding: BottomSheetRecyclerBinding
    private val menuItems: List<MenuItemData> by lazy { getOptionsList() }
    var onMenuItemPressed: ((menuItem: MenuItemData) -> Unit)? = null

    private fun getOptionsList(): List<MenuItemData> {
        return requireArguments()
            .getParcelableArrayList<MenuItemData>(EXTRA_MENU_ITEMS)
            .orEmpty()
    }

    companion object Factory {
        private const val EXTRA_MENU_ITEMS = "extraMenuItems"

        fun newOnlineExamsInstance(examStatus: OnlineExamStatus):MenuBottomSheet {
            val menuItems: List<MenuItemData> = mutableListOf<MenuItemData>().apply {
                if (examStatus != OnlineExamStatus.INACTIVE){
                    add(MenuItemData(R.string.the_answers, R.drawable.ic_plus))
                }
                add(MenuItemData(R.string.the_questions, R.drawable.ic_plus))
                if (examStatus == OnlineExamStatus.INACTIVE){
                    add(MenuItemData(R.string.activate, R.drawable.ic_plus))
                }
                if (examStatus == OnlineExamStatus.ACTIVE){
                    add(MenuItemData(R.string.finish, R.drawable.ic_plus))
                }
                add(MenuItemData(R.string.delete, R.drawable.ic_plus))
            }

            return newInstance(menuItems)
        }

        private fun newInstance(menuItemData: List<MenuItemData>): MenuBottomSheet {
            return MenuBottomSheet().apply {
                arguments = bundleOf(
                    EXTRA_MENU_ITEMS to ArrayList(menuItemData)
                )
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomSheetRecyclerBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@MenuBottomSheet
            recycler.adapter = MenuItemsQuickAdapter().apply {
                setNewInstance(menuItems.toMutableList())
                setOnItemClickListener(::onItemPressed)
            }
        }

        return binding.root
    }

    private fun onItemPressed(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        onMenuItemPressed?.invoke(menuItems[position])
        dismiss()
    }

}