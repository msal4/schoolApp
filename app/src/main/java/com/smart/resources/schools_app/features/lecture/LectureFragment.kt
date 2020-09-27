package com.smart.resources.schools_app.features.lecture

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.extentions.openUrl
import com.smart.resources.schools_app.core.extentions.showSnackBar
import com.smart.resources.schools_app.databinding.FragmentRecyclerLoaderBinding
import com.smart.resources.schools_app.sharedUi.activity.SectionActivity


class LectureFragment : Fragment() {
    private lateinit var binding: FragmentRecyclerLoaderBinding
    private lateinit var adapter: LectureRecyclerAdapter
    private val viewModel: LectureViewModel by activityViewModels()

    companion object {
        fun newInstance(fm: FragmentManager) {

            val fragment = LectureFragment()
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
            LectureRecyclerAdapter().apply {
                adapter = this
                recyclerView.adapter = this
                onItemClick = ::onLectureClicked
            }
            lifecycleOwner = this@LectureFragment
        }
        (activity as SectionActivity).setCustomTitle(R.string.lectures)

        setupViewModel()
        return binding.root
    }

    private fun setupViewModel() {
        viewModel.apply {
            binding.listState = listState
            lectures.observe(viewLifecycleOwner,  { onLectureDownload(it) })
        }
    }

    private fun onLectureDownload(result: List<LectureModel>?) {
        result?.let {
            binding.errorText.text = ""
            adapter.submitList(result)
        }
    }

    private fun onLectureClicked(lectureModel: LectureModel) {
        try {
            lectureModel.url?.openUrl(requireContext())
        } catch (e: Exception) {
            (binding.root as ViewGroup).showSnackBar(e.message.toString())
        }
    }

}

