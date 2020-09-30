package com.smart.resources.schools_app.features.onlineExam.presentation.fragment

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.extentions.hide
import com.smart.resources.schools_app.core.extentions.show
import com.smart.resources.schools_app.core.extentions.openKeyboard
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
            bindQuestionType()
        }

        viewModel.onQuestionAdded.observe(viewLifecycleOwner){
            it?.getContentIfNotHandled()?.let { question ->
                onQuestionAdded?.invoke(question)
            }
        }
        return binding.root
    }

    private fun FragmentAddQuestionBinding.setQuestionFieldEditorActionListener() {
        questionField.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                viewModel.addQuestion()
                true
            } else false
        }
    }
    private fun FragmentAddQuestionBinding.bindQuestionType() {
        questionTypesRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            viewModel.questionType.value = when (checkedId) {
                R.id.multiChoiceQuestionRadioBtn -> {
                    questionField.inputType= InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_MULTI_LINE
                    QuestionType.MULTI_CHOICE
                }
                R.id.correctnessQuestionRadioBtn -> {
                    questionField.inputType= InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_NORMAL
                    QuestionType.CORRECTNESS
                }
                else -> {
                    questionField.inputType= InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_NORMAL
                    QuestionType.NORMAL
                }
            }
        }
        viewModel.questionType.observe(viewLifecycleOwner) {
            val radioBtnId = when (it) {
                QuestionType.MULTI_CHOICE -> R.id.multiChoiceQuestionRadioBtn
                QuestionType.CORRECTNESS -> R.id.correctnessQuestionRadioBtn
                else -> R.id.normalQuestionRadioBtn
            }

            questionTypesRadioGroup.check(radioBtnId)
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

