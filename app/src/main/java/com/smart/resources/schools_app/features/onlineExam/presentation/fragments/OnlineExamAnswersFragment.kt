package com.smart.resources.schools_app.features.onlineExam.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import com.chad.library.adapter.base.BaseQuickAdapter
import com.haytham.coder.extensions.toString
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.activity.SectionActivity
import com.smart.resources.schools_app.core.extentions.createGridLayout
import com.smart.resources.schools_app.databinding.FragmentOnlineExamAnswersBinding
import com.smart.resources.schools_app.features.onlineExam.domain.model.onlineExam.OnlineExam
import com.smart.resources.schools_app.features.onlineExam.domain.viewModel.OnlineExamAnswersViewModel
import com.smart.resources.schools_app.features.onlineExam.presentation.adapter.StudentsQuickAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnlineExamAnswersFragment : Fragment() {
    private lateinit var binding: FragmentOnlineExamAnswersBinding
    private lateinit var adapter: StudentsQuickAdapter
    private val viewModel: OnlineExamAnswersViewModel by viewModels()

    companion object Factory {
        private const val TAG = "OnlineExamAnswersFragment"
        const val EXTRA_ONLINE_EXAM = "extraOnlineExam"

        fun newInstance(fm: FragmentManager, onlineExam: OnlineExam) {
            val fragment = OnlineExamAnswersFragment()
            fragment.arguments = bundleOf(
                ExamPaperFragment.EXTRA_ONLINE_EXAM to onlineExam,
            )
            fm.beginTransaction().apply {
                setCustomAnimations(
                    R.anim.slide_in_right,
                    R.anim.slide_out_left,
                    R.anim.slide_in_left,
                    R.anim.slide_out_right,
                )
                replace(R.id.fragmentContainer, fragment)
                addToBackStack(TAG)
                commit()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOnlineExamAnswersBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@OnlineExamAnswersFragment
            model = viewModel

            setupRecycler()
        }

        setupObservers()
        setupScreenTitle()
        return binding.root
    }

    private fun setupScreenTitle() {
        // test
        val screenTitle = R.string.answers_of_subject.toString(requireContext(), viewModel.passedOnlineExam.subjectName)
        (activity as SectionActivity).setCustomTitle(screenTitle)
    }

    private fun setupObservers() {
        viewModel.students.observe(viewLifecycleOwner) {
            adapter.setNewInstance(it.toMutableList())
        }
    }

    private fun FragmentOnlineExamAnswersBinding.setupRecycler() {
        adapter = StudentsQuickAdapter()
        adapter.setOnItemClickListener(::onStudentClicked)
        recyclerViewLoader.recyclerView.createGridLayout(adapter)
    }

    private fun onStudentClicked(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
       if(isAdded){
           val student= viewModel.students.value?.getOrNull(position)?:return
           if(student.hasAnswer != 1) return

           ExamPaperFragment.newInstance(
               fm = parentFragmentManager,
               exam = viewModel.passedOnlineExam,
               studentId = student.id,
           )
       }
    }
}