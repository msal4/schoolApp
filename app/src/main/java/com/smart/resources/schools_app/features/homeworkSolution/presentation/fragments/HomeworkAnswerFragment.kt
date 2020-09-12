package com.smart.resources.schools_app.features.homeworkSolution.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.databinding.FragmentRecyclerLoaderBinding
import com.smart.resources.schools_app.features.homeworkSolution.domain.viewModel.HomeworkSolutionViewModel
import com.smart.resources.schools_app.features.homeworkSolution.domain.model.HomeworkSolutionModel
import com.smart.resources.schools_app.features.homeworkSolution.presentation.adapter.HomeworkAnswerRecyclerAdapter
import com.smart.resources.schools_app.sharedUi.SectionActivity

class HomeworkAnswerFragment : Fragment() {
    private lateinit var binding: FragmentRecyclerLoaderBinding
    private lateinit var viewModel: HomeworkSolutionViewModel
    private lateinit var adapter: HomeworkAnswerRecyclerAdapter


    companion object {
        private const val HOMEWORK_ANSWER_FRAGMENT = "HomeworkAnswerFragment"

        fun newInstance(fm: FragmentManager) {
            val fragment = HomeworkAnswerFragment()
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
        viewModel = ViewModelProviders.of(activity!!).get(HomeworkSolutionViewModel::class.java)
        binding = FragmentRecyclerLoaderBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@HomeworkAnswerFragment
            listState = viewModel.listState

            adapter = HomeworkAnswerRecyclerAdapter()
            recyclerView.adapter = adapter

            viewModel.answers.observe(viewLifecycleOwner, ::onAnswersReceived)
        }

        (activity as SectionActivity).setCustomTitle(R.string.answers)
        return binding.root
    }

    private fun onAnswersReceived(solutions:List<HomeworkSolutionModel>?){
            adapter.submitList(solutions)
    }

}
