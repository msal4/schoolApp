package com.smart.resources.schools_app.features.onlineExam.presentation.bottomSheets

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.smart.resources.schools_app.core.extentions.hideKeyboard
import com.smart.resources.schools_app.databinding.BottomSheetOnlineExamKeyBinding
import com.smart.resources.schools_app.features.onlineExam.domain.viewModel.ExamKeySheetViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExamKeyBottomSheet : BottomSheetDialogFragment() {
    private lateinit var binding: BottomSheetOnlineExamKeyBinding
    private val viewModelKey:ExamKeySheetViewModel by viewModels()
    var onExamKeyMatch:(()->Unit)?= null

    companion object Factory {
        const val EXTRA_EXAM_ID= "extraExamId"

        fun newInstance(examId:String): ExamKeyBottomSheet {
            return ExamKeyBottomSheet().apply {
                arguments= bundleOf(
                    EXTRA_EXAM_ID to examId
                )
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomSheetOnlineExamKeyBinding.inflate(inflater, container, false).apply {
            lifecycleOwner= this@ExamKeyBottomSheet
            model= viewModelKey

        }

        observeViewModel()
        return binding.root
    }

    private fun observeViewModel() {
        viewModelKey.onExamAdded.observe(viewLifecycleOwner) {
            it?.getContentIfNotHandled()?.let { examAdded ->
                if (examAdded) {
                    view?.hideKeyboard()
                    onExamKeyMatch?.invoke()
                    dismiss()
                }
            }
        }
    }




}