package com.smart.resources.schools_app.features.onlineExam.presentaion.fragment

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
import com.smart.resources.schools_app.core.Event
import com.smart.resources.schools_app.core.extentions.setSoftInputMode
import com.smart.resources.schools_app.core.extentions.setStatusBarColor
import com.smart.resources.schools_app.core.extentions.setStatusBarColorToWhite
import com.smart.resources.schools_app.core.extentions.toColor
import com.smart.resources.schools_app.core.helpers.ViewPager2Helper
import com.smart.resources.schools_app.databinding.FragmentQuestionsBinding
import com.smart.resources.schools_app.features.onlineExam.domain.model.OnlineExamDetails
import com.smart.resources.schools_app.features.onlineExam.domain.viewModel.QuestionsViewModel
import com.smart.resources.schools_app.features.onlineExam.domain.viewModel.QuestionsViewModelFactory
import com.smart.resources.schools_app.features.onlineExam.presentaion.adapter.QuestionsNavigatorAdapter
import com.smart.resources.schools_app.features.onlineExam.presentaion.adapter.QuestionsPagerAdapter
import com.smart.resources.schools_app.sharedUi.SectionActivity
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator

class QuestionsFragment : Fragment(), QuestionsPagerAdapter.Listener {
    private lateinit var binding: FragmentQuestionsBinding
    private val viewModel: QuestionsViewModel by viewModels {
        QuestionsViewModelFactory(arguments?.getParcelable(EXTRA_EXAM_DETAILS)!!)
    }
    private lateinit var pagerAdapter: QuestionsPagerAdapter
    private lateinit var navigatorAdapter: QuestionsNavigatorAdapter

    companion object {
        private const val QUESTIONS_FRAGMENT = "questionsFragment"
        private const val EXTRA_EXAM_DETAILS = "extraExamDetails"

        fun newInstance(fm: FragmentManager, examDetails: OnlineExamDetails) {
            val fragment = QuestionsFragment()
            fragment.arguments = bundleOf(
                EXTRA_EXAM_DETAILS to examDetails
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

        binding = FragmentQuestionsBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@QuestionsFragment
            model = viewModel
            pagerAdapter = QuestionsPagerAdapter()
            pagerAdapter.listener = this@QuestionsFragment
            questionsPager.adapter = pagerAdapter

            magicIndicator.navigator = CommonNavigator(requireContext()).apply {
                navigatorAdapter = QuestionsNavigatorAdapter()
                navigatorAdapter.onItemPressed = ::onPageNumberPressed
                adapter = navigatorAdapter
            }

            ViewPager2Helper.bind(magicIndicator, questionsPager)

            viewModel.questions.observe(viewLifecycleOwner) {
                pagerAdapter.submitList(it)
            }

            viewModel.solvedQuestions.observe(viewLifecycleOwner) {
                navigatorAdapter.submitList(it?: emptyList())
            }

        }

        val screenTitle = requireContext().getString(R.string.questionsOfSubject, "مادة")
        return binding.root
    }


    private fun onPageNumberPressed(index: Int) {
        binding.questionsPager.setCurrentItem(index, true)
    }

    override fun onQuestionAnswerStateUpdated() {
        viewModel.answersStateUpdatedEvent.value= Event(true)
    }
}

