package com.smart.resources.schools_app.features.lecture

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.haytham.coder.extensions.openPdfViewer
import com.haytham.coder.extensions.toString
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.activity.SectionActivity
import com.smart.resources.schools_app.core.extentions.isPdf
import com.smart.resources.schools_app.core.extentions.showSnackBar
import com.smart.resources.schools_app.databinding.FragmentRecyclerLoaderBinding
import com.smart.resources.schools_app.features.schedule.ScheduleDayFragment
import com.smart.resources.schools_app.features.subject.Subject
import com.smart.resources.schools_app.features.subject.SubjectFragment
import com.smart.resources.schools_app.features.youtubePlayer.YoutubePlayerActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.item_lecture.*

@AndroidEntryPoint
class LectureFragment : Fragment() {
    private lateinit var binding: FragmentRecyclerLoaderBinding
    private lateinit var adapter: LectureRecyclerAdapter
    private val viewModel: LectureViewModel by viewModels()

    companion object {
        private const val LECTURE_FRAGMENT = "lectureFragment"

        fun newInstance(fm: FragmentManager, subject: Subject) {

            val fragment = LectureFragment()

            Bundle().apply {
                putInt("subjectId", subject.idSubject)
                fragment.arguments = this
            }

            fm.beginTransaction().apply {
                setCustomAnimations(
                    R.anim.slide_in_right,
                    R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right
                )
                replace(R.id.fragmentContainer, fragment)
                addToBackStack(LECTURE_FRAGMENT)
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
                onItemPdfClick = ::onViewPDFClicked
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
            val subjectId = arguments?.getInt("subjectId")!!
            getLectures(subjectId).observe(viewLifecycleOwner) { onLectureDownload(it) }
        }
    }

    private fun onLectureDownload(result: List<LectureModel>?) {
        result?.let {
            binding.errorText.text = ""
            adapter.submitList(result)
        }
    }

    private fun onViewPDFClicked(lectureModel: LectureModel) {
        if(lectureModel.pdfURL.isPdf()) context?.openPdfViewer(lectureModel.pdfURL)
    }

    private fun onLectureClicked(lectureModel: LectureModel) {
        if(lectureModel.url.isBlank()){
            binding.layout.showSnackBar(R.string.invalid_url.toString(requireContext()))
        }else {
            YoutubePlayerActivity.newInstance(requireContext(), lectureModel.url!!)
        }

    }

}

