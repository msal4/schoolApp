package com.smart.resources.schools_app.features.subject

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.activity.SectionActivity
import com.smart.resources.schools_app.databinding.FragmentRecyclerLoaderBinding
import com.smart.resources.schools_app.features.lecture.LectureFragment
import com.smart.resources.schools_app.features.schedule.ScheduleDayFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SubjectFragment : Fragment() {
    private lateinit var binding: FragmentRecyclerLoaderBinding
    private lateinit var adapter: SubjectRecyclerAdapter
    private val viewModel: SubjectViewModel by viewModels()

    companion object {
        fun newInstance(fm: FragmentManager) {

            val fragment = SubjectFragment()
            fm.beginTransaction().apply {
                add(R.id.fragmentContainer, fragment)
                commit()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecyclerLoaderBinding.inflate(inflater, container, false).apply {
            SubjectRecyclerAdapter().apply {
                adapter = this
                recyclerView.adapter = this
                onItemClick = ::onSubjectClicked
            }
            lifecycleOwner = this@SubjectFragment
        }
        (activity as SectionActivity).setCustomTitle(R.string.lectures)

        setupViewModel()
        return binding.root
    }

    private fun setupViewModel() {
        viewModel.apply {
            binding.listState = listState
            subjects.observe(viewLifecycleOwner) { onLectureDownload(it) }
        }
    }

    private fun onLectureDownload(result: List<Subject>?) {
        result?.let {
            binding.errorText.text = ""
            adapter.submitList(result)
        }
    }

    private fun onSubjectClicked(subject: Subject) {
        fragmentManager?.let {
            LectureFragment.newInstance(
                it,
                subject
            )
        }

    }

}

