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
        val questionVal = question.value
        val questionTypeVal = questionType.value


        if (questionTypeVal == null) {
            _questionErrorMsg.postValue(R.string.no_question_type_selected_msg)
            return
        }
        if (questionVal.isNullOrBlank()) {
            _questionErrorMsg.postValue(R.string.empty_question_msg)
            return
        }
        val parts = questionVal.split("\n").filter { !it.isBlank() }
        if (questionTypeVal == QuestionType.MULTI_CHOICE) {

            if (parts.size < 2) {
                _questionErrorMsg.value = R.string.empty_options_error_msg
                return
            }
        }

        val questionModel = Question(
            id = count.toString(),
            question = questionVal,
            questionType = questionTypeVal,
            options = parts.subList(1, parts.size)
        )

        _onQuestionAdded.value = Event(questionModel)
        question.value= ""
        Logger.d("$TAG: $questionModel")
    }

}
