package com.smart.resources.schools_app.features.homeworkSolution.presentation.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.smart.resources.schools_app.core.myTypes.Event
import com.smart.resources.schools_app.core.extentions.GET_IMAGE_REQUEST
import com.smart.resources.schools_app.core.extentions.getImage
import com.smart.resources.schools_app.core.extentions.selectImage
import com.smart.resources.schools_app.core.extentions.showSnackBar
import com.smart.resources.schools_app.databinding.BottomSheetAnswerHomeworkBinding
import com.smart.resources.schools_app.features.homeworkSolution.data.model.HomeworkSolutionModel
import com.smart.resources.schools_app.features.homeworkSolution.domain.viewModel.AddHomeworkSolutionViewModel

typealias solutionAddedCallback= (homeworkSolution:HomeworkSolutionModel)->Unit
class AddHomeworkSolutionBottomSheet : BottomSheetDialogFragment() {
    private lateinit var binding: BottomSheetAnswerHomeworkBinding
    private lateinit var viewModel: AddHomeworkSolutionViewModel
    var onSolutionAdded: solutionAddedCallback?= null

    companion object Factory {
        private const val EXTRA_HOMEWORK_ID = "extraHomeworkId"

        fun newInstance(homeworkId: String): AddHomeworkSolutionBottomSheet {
            return AddHomeworkSolutionBottomSheet().apply {
                arguments = bundleOf(
                    EXTRA_HOMEWORK_ID to homeworkId
                )
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders
            .of(this)
            .get(AddHomeworkSolutionViewModel::class.java).apply {
                error.observe(viewLifecycleOwner, ::onError)
                solutionSent.observe(viewLifecycleOwner, ::onSolutionSent)
                homeworkId= arguments?.getString(EXTRA_HOMEWORK_ID)?:""
            }

        binding = BottomSheetAnswerHomeworkBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@AddHomeworkSolutionBottomSheet
            model = viewModel
            addImageIcon.setOnClickListener { selectImage() }
        }

        return binding.root
    }

    private fun onError(errorEvent: Event<String>?) {
        errorEvent?.getContentIfNotHandled()?.let {
            binding.coordinatorLayout.showSnackBar(it)
        }
    }

    private fun onSolutionSent(solutionSentEvent: Event<HomeworkSolutionModel>?) {
        solutionSentEvent?.getContentIfNotHandled()?.let {
            dismiss()
            onSolutionAdded?.invoke(it)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GET_IMAGE_REQUEST &&
            resultCode == Activity.RESULT_OK &&
            data != null
        ) {
            data.getImage()?.let {
                viewModel.saveUri(it)
                binding.apply {
                    imageAttachment.setImageURI(it)
                    imageCard.visibility = View.VISIBLE
                }
            }
        }
    }


}