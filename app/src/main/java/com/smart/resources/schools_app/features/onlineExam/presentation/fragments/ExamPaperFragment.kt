package com.smart.resources.schools_app.features.onlineExam.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import com.haytham.coder.extensions.setSoftInputMode
import com.haytham.coder.extensions.setStatusBarColor
import com.haytham.coder.extensions.setStatusBarColorToWhite
import com.haytham.coder.extensions.toColor
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.activity.SectionActivity
import com.smart.resources.schools_app.core.callbacks.ViewPager2Helper
import com.smart.resources.schools_app.databinding.FragmentExamPaperBinding
import com.smart.resources.schools_app.features.onlineExam.domain.model.BaseAnswer
import com.smart.resources.schools_app.features.onlineExam.domain.model.onlineExam.OnlineExam
import com.smart.resources.schools_app.features.onlineExam.domain.viewModel.ExamPaperViewModel
import com.smart.resources.schools_app.features.onlineExam.presentation.adapter.AnswerableQuestionsPagerAdapter
import com.smart.resources.schools_app.features.onlineExam.presentation.adapter.QuestionsNavigatorAdapter
import dagger.hilt.android.AndroidEntryPoint
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator

@AndroidEntryPoint
class ExamPaperFragment : Fragment(), AnswerableQuestionsPagerAdapter.Listener {
    private lateinit var binding: FragmentExamPaperBinding
    private val viewModel: ExamPaperViewModel by viewModels()

    private lateinit var pagerAdapter: AnswerableQuestionsPagerAdapter
    private lateinit var navigatorAdapter: QuestionsNavigatorAdapter

    companion object {
        private const val QUESTIONS_FRAGMENT = "questionsFragment"
        const val EXTRA_EXAM_DETAILS = "extraExamDetails"

        fun newInstance(fm: FragmentManager, exam: OnlineExam) {
            val fragment = ExamPaperFragment()
            fragment.arguments = bundleOf(
                EXTRA_EXAM_DETAILS to exam,
            )
            fm.beginTransaction().apply {
                setCustomAnimations(
                    R.anim.slide_in_right,
                    R.anim.slide_out_left,
                    R.anim.slide_in_left,
                    R.anim.slide_out_right,
                )
                replace(R.id.fragmentContainer, fragment)
                addToBackStack(QUESTIONS_FRAGMENT)
                commit()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN or WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        (activity as SectionActivity).hideToolbar()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.setStatusBarColorToWhite(view)
    }

    override fun onDestroy() {
        super.onDestroy()
        activity?.apply {
            setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN or WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
            setStatusBarColor(R.color.colorPrimaryDark.toColor(requireContext()))
        }
        (activity as SectionActivity).showToolbar()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentExamPaperBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@ExamPaperFragment
            model = viewModel

            setupViewPager()
        }
        observeViewModel()
        return binding.root
    }

    private fun observeViewModel() {
        viewModel.apply {
            questions.observe(viewLifecycleOwner) {
                pagerAdapter.submitList(it)
            }

            solvedQuestions.observe(viewLifecycleOwner) {
                navigatorAdapter.submitList(it ?: emptyList())
            }

            readOnly.observe(viewLifecycleOwner) {
                it?.let {
                    pagerAdapter.updateReadOnly(it)
                }
            }
        }
    }

    private fun FragmentExamPaperBinding.setupViewPager() {
        pagerAdapter = AnswerableQuestionsPagerAdapter()
        pagerAdapter.listener = this@ExamPaperFragment
        questionsPager.adapter = pagerAdapter

        magicIndicator.navigator = CommonNavigator(requireContext()).apply {
            navigatorAdapter = QuestionsNavigatorAdapter()
            navigatorAdapter.onItemPressed = ::onPageNumberPressed
            adapter = navigatorAdapter
        }
        ViewPager2Helper.bind(magicIndicator, questionsPager)
    }

    private fun onPageNumberPressed(index: Int) {
        binding.questionsPager.setCurrentItem(index, true)
    }

    override fun onQuestionAnswerStateUpdated(answer: BaseAnswer<out Any>, position: Int) {
        viewModel.updateAnswer(answer, position)
    }


}
