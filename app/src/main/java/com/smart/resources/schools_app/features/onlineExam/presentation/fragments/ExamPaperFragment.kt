package com.smart.resources.schools_app.features.onlineExam.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.haytham.coder.extensions.setStatusBarColor
import com.haytham.coder.extensions.setStatusBarColorToWhite
import com.haytham.coder.extensions.toColor
import com.haytham.coder.extensions.toString
import com.orhanobut.logger.Logger
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.activity.SectionActivity
import com.smart.resources.schools_app.core.callbacks.ViewPager2Helper
import com.smart.resources.schools_app.core.extentions.showSnackBar
import com.smart.resources.schools_app.core.myTypes.Event
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
    private var pageChangeCallback: ViewPager2.OnPageChangeCallback? = null
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
        (activity as SectionActivity).hideToolbar()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.setStatusBarColorToWhite(view)
    }

    override fun onDestroy() {
        super.onDestroy()
        activity?.apply {
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
            answerableQuestions.observe(viewLifecycleOwner) {
                pagerAdapter.submitList(it)
            }

            questionsSolvedState.observe(viewLifecycleOwner) {
                navigatorAdapter.submitList(it ?: emptyList())
            }

            readOnly.observe(viewLifecycleOwner) {
                it?.let { readOnly ->
                    pagerAdapter.updateReadOnly(readOnly)
                    if (readOnly) {
                        unregisterPageChangeCallback()
                    } else {
                        registerPageChangeCallback()
                    }
                }
            }

            errorEvent.observe(viewLifecycleOwner) {
                showMsgIfNeeded(it, true)
            }
            successEvent.observe(viewLifecycleOwner) {
                showMsgIfNeeded(it, false)
            }
        }
    }

    private fun showMsgIfNeeded(it: Event<Int>?, isError: Boolean) {
        it?.getContentIfNotHandled()?.let { msgId ->
            binding.layout.showSnackBar(
                msgId.toString(requireContext()),
                isError = isError,
            )
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

    private fun registerPageChangeCallback() {
        if (pageChangeCallback != null) return

        pageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                viewModel.checkExamStatus()
                Logger.wtf(position.toString())
            }
        }
        pageChangeCallback?.let { binding.questionsPager.registerOnPageChangeCallback(it) }
    }

    private fun unregisterPageChangeCallback() {
        pageChangeCallback?.let { binding.questionsPager.unregisterOnPageChangeCallback(it) }
    }

    private fun onPageNumberPressed(index: Int) {
        binding.questionsPager.setCurrentItem(index, true)
    }

    override fun onQuestionAnswerStateUpdated(answer: BaseAnswer<Any>, position: Int) {
        viewModel.updateAnswer(answer, position)
    }

}



