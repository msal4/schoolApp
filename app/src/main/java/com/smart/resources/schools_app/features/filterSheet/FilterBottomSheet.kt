package com.smart.resources.schools_app.features.filterSheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.chad.library.adapter.base.BaseQuickAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.smart.resources.schools_app.databinding.BottomSheetRecyclerBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FilterBottomSheet : BottomSheetDialogFragment() {
    private lateinit var binding: BottomSheetRecyclerBinding
    private val options: MutableList<FilterItem> by lazy { getOptionsList() }
    var onSelectionChanged: ((selectedPositions: List<Int>) -> Unit)? = null

    private fun getOptionsList(): MutableList<FilterItem> {
        return requireArguments()
            .getParcelableArrayList<FilterItem>(EXTRA_OPTIONS)
            .orEmpty()
            .toMutableList()
    }

    companion object Factory {
        private const val EXTRA_OPTIONS = "extraOptions"

        fun newInstance(filterOptions: List<String>): FilterBottomSheet {
            val filterItems= filterOptions.map { FilterItem(item = it) }
            return newInstance(filterItems)
        }

        @JvmName("newInstance1")
        fun newInstance(filterOptions: List<FilterItem>): FilterBottomSheet {
            return FilterBottomSheet().apply {
                arguments = bundleOf(
                    EXTRA_OPTIONS to filterOptions,
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
            lifecycleOwner = this@FilterBottomSheet

            recycler.adapter = FilterQuickAdapter().apply {
                setNewInstance(options)
                setOnItemClickListener(::onItemPressed)
            }
        }

        return binding.root
    }

    private fun onItemPressed(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        // update data
        val item = options[position]
        options[position] = item.copy(
            selected = !item.selected
        )

        // notify adapter & listener
        adapter.notifyItemChanged(position)
        notifyListener()
    }

    private fun notifyListener() {
        val selectedPositions = options.mapIndexed { index, filterItem ->
            if (filterItem.selected) {
                index
            } else {
                null
            }
        }.filterNotNull()

        onSelectionChanged?.invoke(selectedPositions)
    }
}