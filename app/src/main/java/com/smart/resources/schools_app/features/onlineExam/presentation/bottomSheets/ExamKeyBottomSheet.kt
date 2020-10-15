package com.smart.resources.schools_app.features.onlineExam.presentation.bottomSheets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.smart.resources.schools_app.databinding.BottomSheetAddOnlineExamBinding
import com.smart.resources.schools_app.features.onlineExam.domain.model.onlineExam.OnlineExam
import com.smart.resources.schools_app.features.onlineExam.domain.viewModel.AddOnlineExamSheetViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExamKeyBottomSheet : BottomSheetDialogFragment() {
    private lateinit var binding: BottomSheetAddOnlineExamBinding
    private val viewModel:AddOnlineExamSheetViewModel by viewModels()
    var onExamKeyMatch:(()->Unit)?= null

    companion object Factory {

        fun newInstance(): ExamKeyBottomSheet {
            return ExamKeyBottomSheet()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomSheetAddOnlineExamBinding.inflate(inflater, container, false).apply {
            lifecycleOwner= this@ExamKeyBottomSheet
            model= viewModel

        }

        observeViewModel()
        return binding.root
    }

    private fun observeViewModel() {
        viewModel.onExamAdded.observe(viewLifecycleOwner) {
            it?.getContentIfNotHandled()?.let { examAdded ->
                if (examAdded) {
                    onExamKeyMatch?.invoke()
                    dismiss()
                }
            }
        }
    }




}