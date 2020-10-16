package com.smart.resources.schools_app.features.onlineExam.presentation.bottomSheets

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.haytham.coder.extensions.hide
import com.haytham.coder.extensions.openKeyboard
import com.haytham.coder.extensions.show
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.typeConverters.room.QuestionType
import com.smart.resources.schools_app.databinding.FragmentAddQuestionBinding
import com.smart.resources.schools_app.features.onlineExam.domain.model.Question
import com.smart.resources.schools_app.features.onlineExam.domain.viewModel.AddQuestionViewModel

class AddQuestionFragment : Fragment() {
    private lateinit var binding: FragmentAddQuestionBinding
    private val viewModel: AddQuestionViewModel by viewModels()
    var onQuestionAdded: ((question: Question) -> Unit)? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAddQuestionBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@AddQuestionFragment
            model = viewModel

            setFieldFocusListener()
            setQuestionFieldEditorActionListener()
            setRadioBtnListener()
        }

        observeOnQuestionAdded()
        observeQuestionType()
        return binding.root
    }

    private fun observeOnQuestionAdded() {
        viewModel.onQuestionAdded.observe(viewLifecycleOwner) {
            it?.getContentIfNotHandled()?.let { question ->
                onQuestionAdded?.invoke(question)
            }
        }
    }

    private fun FragmentAddQuestionBinding.setQuestionFieldEditorActionListener() {
        questionField.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                viewModel.addQuestion()
                true
            } else false
        }
    }

    private fun observeQuestionType() {
        binding.apply {
            viewModel.questionType.observe(viewLifecycleOwner) {
                val radioBtn = when (it) {
                    QuestionType.MULTI_CHOICE -> multiChoiceQuestionRadioBtn
                    QuestionType.CORRECTNESS -> correctnessQuestionRadioBtn
                    QuestionType.DEFINE -> defineQuestionRadioBtn
                    QuestionType.WHY -> whyQuestionRadioBtn
                    else -> answerQuestionRadioBtn
                }
                radioGroupTable.setChecked(radioBtn)
            }
        }
    }

    private fun FragmentAddQuestionBinding.setRadioBtnListener() {
        radioGroupTable.onActiveBtnChanged = {
            val checkedBtnId = radioGroupTable.checkedRadioButtonId
            viewModel.questionType.value = when (checkedBtnId) {
                R.id.multiChoiceQuestionRadioBtn -> {
                    QuestionType.MULTI_CHOICE
                }
                R.id.correctnessQuestionRadioBtn -> {
                    QuestionType.CORRECTNESS
                }
                R.id.defineQuestionRadioBtn -> {
                    QuestionType.DEFINE
                }
                R.id.whyQuestionRadioBtn -> {
                    QuestionType.WHY
                }
                else -> {
                    QuestionType.ANSWER_THE_FOLLOWING
                }
            }
            questionField.inputType = InputType.TYPE_CLASS_TEXT or
                    if (checkedBtnId == R.id.multiChoiceQuestionRadioBtn) InputType.TYPE_TEXT_FLAG_MULTI_LINE
                    else InputType.TYPE_TEXT_VARIATION_NORMAL

            questionField.setSelection(questionField.text?.length ?: 0)
        }
    }

    fun show() {
        requireContext().openKeyboard()
        binding.questionField.requestFocus()
    }

    fun hide() {
        binding.questionField.clearFocus()
    }

    private fun FragmentAddQuestionBinding.setFieldFocusListener() {
        questionField.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) root.show()
            else root.hide()

        }
    }
}

