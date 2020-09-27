package com.smart.resources.schools_app.features.homeworkSolution.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.smart.resources.schools_app.databinding.BottomSheetHomeworkSolutionBinding
import com.smart.resources.schools_app.features.homeworkSolution.data.model.HomeworkSolutionModel
import com.smart.resources.schools_app.features.imageViewer.ImageViewerActivity

class ShowHomeworkSolutionBottomSheet : BottomSheetDialogFragment() {
    private lateinit var binding: BottomSheetHomeworkSolutionBinding
    private lateinit var homeworkSolution: HomeworkSolutionModel

    companion object Factory {
        private const val EXTRA_SOLUTION = "homeworkSolution"

        fun newInstance(homeworkSolution: HomeworkSolutionModel): ShowHomeworkSolutionBottomSheet {
            return ShowHomeworkSolutionBottomSheet().apply {
                arguments = bundleOf(
                    EXTRA_SOLUTION to homeworkSolution
                )
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeworkSolution = arguments?.getParcelable(EXTRA_SOLUTION)!!

        binding = BottomSheetHomeworkSolutionBinding.inflate(inflater, container, false).apply {
            model = homeworkSolution
            imageCard.setOnClickListener {
                activity?.let { a ->
                    ImageViewerActivity.newInstance(
                        a,
                        imageAttachment,
                        homeworkSolution.imageUrl.toString()
                    )
                }
            }
        }

        return binding.root
    }


}