package com.smart.resources.schools_app.features.onlineExam.domain.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.orhanobut.logger.Logger
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.myTypes.Event
import com.smart.resources.schools_app.core.typeConverters.room.QuestionType
import com.smart.resources.schools_app.features.onlineExam.domain.model.Question

class AddQuestionViewModel : ViewModel() {

    companion object {
        private const val TAG = "AddQuestionViewModel"
        private const val MIN_QUESTION_OPTIONS= 2
        private const val MAX_QUESTION_OPTIONS= 3
    }

    private var count= 0

    val question = MutableLiveData("")
    private val _questionErrorMsg = MutableLiveData<Int>()
    val questionErrorMsg: LiveData<Int> = question.switchMap {
        _questionErrorMsg.apply {
            if (value != null) value = null
        }
    }

    val questionType = MutableLiveData(QuestionType.NORMAL)
    private val _onQuestionAdded: MutableLiveData<Event<Question>> = MutableLiveData()
    val onQuestionAdded: LiveData<Event<Question>> = _onQuestionAdded

    fun addQuestion() {
        var questionText = question.value
        var questionOptions:List<String> = emptyList()
        val questionTypeVal = questionType.value


        if (questionTypeVal == null) {
            _questionErrorMsg.postValue(R.string.no_question_type_selected_msg)
            return
        }
        if (questionText.isNullOrBlank()) {
            _questionErrorMsg.postValue(R.string.empty_question_msg)
            return
        }

        val parts = questionText.split("\n").map { it.trim() }.filter { !it.isBlank() }
        if (questionTypeVal == QuestionType.MULTI_CHOICE) {
            val optionsCount= parts.size-1
            if (optionsCount < MIN_QUESTION_OPTIONS) {
                _questionErrorMsg.value = R.string.empty_options_error_msg
                return
            }
            if (optionsCount > MAX_QUESTION_OPTIONS) {
                _questionErrorMsg.value = R.string.too_many_options_error_msg
                return
            }

            questionText= parts[0]
            questionOptions= parts.subList(1, parts.size)
        }

        val questionModel = Question(
            id = count.toString(),
            question = questionText,
            questionType = questionTypeVal,
            options = questionOptions
        )

        _onQuestionAdded.value = Event(questionModel)
        question.value= ""
        Logger.d("$TAG: $questionModel")
    }

}
