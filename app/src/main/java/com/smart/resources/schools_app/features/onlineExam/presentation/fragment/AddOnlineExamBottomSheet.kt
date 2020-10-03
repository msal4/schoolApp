package com.smart.resources.schools_app.features.onlineExam.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.smart.resources.schools_app.core.bindingAdapters.setEditTextError
import com.smart.resources.schools_app.databinding.BottomSheetAddOnlineExamBinding
import com.smart.resources.schools_app.features.filter.FilterBottomSheet
import com.smart.resources.schools_app.features.onlineExam.domain.viewModel.AddOnlineExamSheetViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_add_online_exam.*

@AndroidEntryPoint
class AddOnlineExamBottomSheet : BottomSheetDialogFragment() {
    private lateinit var binding: BottomSheetAddOnlineExamBinding
    private val viewModel:AddOnlineExamSheetViewModel by viewModels()

    companion object Factory {

        fun newInstance(): AddOnlineExamBottomSheet {
            return AddOnlineExamBottomSheet()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomSheetAddOnlineExamBinding.inflate(inflater, container, false).apply {
            lifecycleOwner= this@AddOnlineExamBottomSheet
            model= viewModel
        }

        observeViewModel()
        return binding.root
    }

    private fun observeViewModel() {
        viewModel.onExamAdded.observe(viewLifecycleOwner) {
            it?.getContentIfNotHandled()?.let { examAdded ->
                if (examAdded) {
                    dismiss()
                }
            }
        }
    }




}