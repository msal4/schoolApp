package com.smart.resources.schools_app.features.onlineExam.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.activity.SectionActivity
import com.smart.resources.schools_app.core.callbacks.ViewPager2Helper
import com.smart.resources.schools_app.core.extentions.setSoftInputMode
import com.smart.resources.schools_app.core.extentions.setStatusBarColor
import com.smart.resources.schools_app.core.extentions.setStatusBarColorToWhite
import com.smart.resources.schools_app.core.extentions.toColor
import com.smart.resources.schools_app.databinding.FragmentExamPaperBinding
import com.smart.resources.schools_app.features.onlineExam.domain.model.BaseAnswer
import com.smart.resources.schools_app.features.onlineExam.domain.model.OnlineExam
import com.smart.resources.schools_app.features.onlineExam.domain.viewModel.QuestionsViewModel
import com.smart.resources.schools_app.features.onlineExam.domain.viewModel.QuestionsViewModelFactory
import com.smart.resources.schools_app.features.onlineExam.presentation.adapter.AnswerableQuestionsPagerAdapter
import com.smart.resources.schools_app.features.onlineExam.presentation.adapter.QuestionsNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator

class ExamPaperFragment : Fragment(), AnswerableQuestionsPagerAdapter.Listener {
    private lateinit var binding: FragmentExamPaperBinding
    private val viewModel: QuestionsViewModel by viewModels {
        requireArguments().run {
            QuestionsViewModelFactory(
                getParcelable(EXTRA_EXAM_DETAILS)!!,
                getBoolean(EXTRA_READ_ONLY)
            )
        }

    }
    private lateinit var pagerAdapterAnswerable: AnswerableQuestionsPagerAdapter
    private lateinit var navigatorAdapter: QuestionsNavigatorAdapter

    companion object {
        private const val QUESTIONS_FRAGMENT = "questionsFragment"
        private const val EXTRA_EXAM_DETAILS = "extraExamDetails"
        private const val EXTRA_READ_ONLY = "extraReadOnly"

        fun newInstance(fm: FragmentManager, exam: OnlineExam, readOnly: Boolean = false) {
            val fragment = ExamPaperFragment()
            fragment.arguments = bundleOf(
                EXTRA_EXAM_DETAILS to exam,
                EXTRA_READ_ONLY to readOnly
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
            pagerAdapterAnswerable = AnswerableQuestionsPagerAdapter(viewModel.readOnly)
            pagerAdapterAnswerable.listener = this@ExamPaperFragment
            questionsPager.adapter = pagerAdapterAnswerable

            magicIndicator.navigator = CommonNavigator(requireContext()).apply {
                navigatorAdapter = QuestionsNavigatorAdapter()
                navigatorAdapter.onItemPressed = ::onPageNumberPressed
                adapter = navigatorAdapter
            }

            ViewPager2Helper.bind(magicIndicator, questionsPager)

            viewModel.questions.observe(viewLifecycleOwner) {
                pagerAdapterAnswerable.submitList(it)
            }

            viewModel.solvedQuestions.observe(viewLifecycleOwner) {
                navigatorAdapter.submitList(it ?: emptyList())
            }

        }

        return binding.root
    }

    private fun onPageNumberPressed(index: Int) {
        binding.questionsPager.setCurrentItem(index, true)
    }

    override fun onQuestionAnswerStateUpdated(answer: BaseAnswer<out Any>, position: Int) {
        viewModel.updateAnswer(answer, position)
    }


}

