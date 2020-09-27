package com.smart.resources.schools_app.features.homeworkSolution.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.databinding.FragmentRecyclerLoaderBinding
import com.smart.resources.schools_app.features.homeworkSolution.domain.viewModel.HomeworkSolutionViewModel
import com.smart.resources.schools_app.features.homeworkSolution.data.model.HomeworkSolutionModel
import com.smart.resources.schools_app.features.homeworkSolution.presentation.adapter.HomeworkAnswerRecyclerAdapter
import com.smart.resources.schools_app.features.imageViewer.ImageViewerActivity
import com.smart.resources.schools_app.core.activity.SectionActivity

class HomeworkSolutionFragment : Fragment() {
    private lateinit var binding: FragmentRecyclerLoaderBinding
    private lateinit var adapter: HomeworkAnswerRecyclerAdapter
    private val viewModel: HomeworkSolutionViewModel by activityViewModels()


    companion object {
        private const val HOMEWORK_ANSWER_FRAGMENT = "HomeworkAnswerFragment"
        private const val EXTRA_HOMEWORK_ID = "extraHomeworkId"

        fun newInstance(fm: FragmentManager, homeworkId:String) {
            val fragment = HomeworkSolutionFragment().apply {
                arguments = bundleOf(
                    EXTRA_HOMEWORK_ID to homeworkId
                )
            }
            fm.beginTransaction().apply {
                setCustomAnimations(
                    R.anim.slide_in_right,
                    R.anim.slide_out_left,
                    R.anim.slide_in_left,
                    R.anim.slide_out_right
                )
                replace(R.id.fragmentContainer, fragment)
                addToBackStack(HOMEWORK_ANSWER_FRAGMENT)
                commit()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val homeworkId= arguments?.getString(EXTRA_HOMEWORK_ID)?:""
        viewModel.init(homeworkId)

        binding = FragmentRecyclerLoaderBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@HomeworkSolutionFragment
            listState = viewModel.listState

            adapter = HomeworkAnswerRecyclerAdapter()
            adapter.onImageClicked= ::onImageClicked
            recyclerView.adapter = adapter

            viewModel.answers.observe(viewLifecycleOwner, ::onAnswersReceived)
        }

        (activity as SectionActivity).setCustomTitle(R.string.answers)
        return binding.root
    }


    private fun onImageClicked(imageView: ImageView, imageUrl: String) {
        activity?.let {
            ImageViewerActivity.newInstance(it, imageView, imageUrl)
        }
    }

    private fun onAnswersReceived(solutions:List<HomeworkSolutionModel>?){
            adapter.submitList(solutions)
    }

}
