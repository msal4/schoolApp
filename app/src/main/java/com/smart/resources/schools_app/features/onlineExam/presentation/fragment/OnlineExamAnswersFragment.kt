package com.smart.resources.schools_app.features.onlineExam.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.activity.SectionActivity
import com.smart.resources.schools_app.core.bindingAdapters.setEditTextError
import com.smart.resources.schools_app.core.extentions.createGridLayout
import com.smart.resources.schools_app.core.extentions.toString
import com.smart.resources.schools_app.databinding.BottomSheetAddOnlineExamBinding
import com.smart.resources.schools_app.databinding.FragmentOnlineExamAnswersBinding
import com.smart.resources.schools_app.features.filter.FilterBottomSheet
import com.smart.resources.schools_app.features.onlineExam.domain.viewModel.AddOnlineExamSheetViewModel
import com.smart.resources.schools_app.features.onlineExam.domain.viewModel.OnlineExamAnswersViewModel
import com.smart.resources.schools_app.features.onlineExam.presentation.adapter.StudentsQuickAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_add_online_exam.*

@AndroidEntryPoint
class OnlineExamAnswersFragment : Fragment() {
    private lateinit var binding: FragmentOnlineExamAnswersBinding
    private lateinit var adapter:StudentsQuickAdapter
    private val viewModel:OnlineExamAnswersViewModel by viewModels()

    companion object Factory {
        private const val TAG = "OnlineExamAnswersFragment"

        fun newInstance(fm: FragmentManager) {
            val fragment = OnlineExamAnswersFragment()
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
            lifecycleOwner= this@OnlineExamAnswersFragment
            model= viewModel

            setupRecycler()
        }

        setupObservers()
        setupScreenTitle()
        return binding.root
    }

    private fun setupScreenTitle() {
        // test
        val screenTitle = R.string.answers_of_subject.toString(requireContext(), "مادة")
        (activity as SectionActivity).setCustomTitle(screenTitle)
    }

    private fun setupObservers() {
        viewModel.students.observe(viewLifecycleOwner) {
            adapter.setNewInstance(it?.toMutableList())
        }
    }

    private fun FragmentOnlineExamAnswersBinding.setupRecycler() {
        adapter = StudentsQuickAdapter()
        adapter.setOnItemClickListener { adapter, view, position ->

        }
        recyclerViewLoader.recyclerView.createGridLayout(adapter)
    }
}