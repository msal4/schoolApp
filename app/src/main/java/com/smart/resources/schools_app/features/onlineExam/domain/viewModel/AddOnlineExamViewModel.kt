package com.smart.resources.schools_app.features.onlineExam.domain.viewModel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.orhanobut.logger.Logger
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.extentions.isNotNullOrBlank
import com.smart.resources.schools_app.core.extentions.isValidIndex
import com.smart.resources.schools_app.core.myTypes.Event
import com.smart.resources.schools_app.core.typeConverters.room.OnlineExamStatus
import com.smart.resources.schools_app.core.typeConverters.room.QuestionType
import com.smart.resources.schools_app.features.login.CanLogout
import com.smart.resources.schools_app.features.onlineExam.domain.model.CompleteOnlineExam
import com.smart.resources.schools_app.features.onlineExam.domain.model.OnlineExam
import com.smart.resources.schools_app.features.onlineExam.domain.model.Question
import com.smart.resources.schools_app.features.onlineExam.domain.usecase.IAddOnlineExamsUseCase
import com.smart.resources.schools_app.features.users.domain.usecase.IGetCurrentTeacherDataUseCase
import kotlinx.coroutines.launch
import org.threeten.bp.Duration
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.LocalTime


class AddOnlineExamViewModel @ViewModelInject constructor(
    private val addOnlineExamsUseCase: IAddOnlineExamsUseCase,
    private val getCurrentTeacherDataUseCase: IGetCurrentTeacherDataUseCase

) : ViewModel(),
    CanLogout {
    companion object {
        private const val TAG = "AddOnlineExamViewModel"
    }

    private val _showErrors = MutableLiveData<Boolean>(false)
    private val _isLoading = MutableLiveData<Event<Boolean>>(Event(false))
    private val _questions = MutableLiveData<List<Question>>(dummyQuestions) // TODO: remove initial data
    private val _errorMsgEvent = MutableLiveData<Event<Int?>>()
    private val _examAddedEvent = MutableLiveData<Event<Boolean>>()


    val subjectsList = liveData {
        val subjects = getCurrentTeacherDataUseCase()?.subjects.orEmpty()
        emit(subjects)
    }
    val examSubjectIndex = MutableLiveData<Int>(0)
    val examDate = MutableLiveData<LocalDate>(null)
    val examTime = MutableLiveData<LocalTime>(null)
    val examDurationInMinutes = MutableLiveData<Int>(250)
    val numberOfRequiredQuestions = MutableLiveData<Int>(1)
    val examKey = MutableLiveData<String>("")
    val questions: LiveData<List<Question>> = _questions




    val subjectErrorMsg: LiveData<Int?> = examSubjectIndex.map {
        val subjectsCount = subjectsList.value?.size ?: 0
        if (it.isValidIndex(subjectsCount)) null
        else R.string.subject_not_selected_error
    }
    val examDateErrorMsg: LiveData<Int?> = examDate.map {
        if (it != null) null
        else R.string.empty_exam_date_error
    }
    val examTimeErrorMsg: LiveData<Int?> = examTime.map {
        if (it != null) null
        else R.string.empty_exam_time_error
    }
    val examKeyErrorMsg: LiveData<Int?> = examKey.map {
        if (it.isNotNullOrBlank()) null
        else R.string.empty_exam_key_error
    }
    private var questionsListError:Int?= null

    val errorMsgEvent: LiveData<Event<Int?>> = _errorMsgEvent
    val examAddedEvent: LiveData<Event<Boolean>> = _examAddedEvent
    val showErrors: LiveData<Boolean> = _showErrors
    val isLoading: LiveData<Event<Boolean>> = _isLoading


    private val isFormValid= MediatorLiveData<Boolean>().apply {
       addSource(subjectErrorMsg){
           value= isFormValid()
       }
        addSource(examDateErrorMsg){
            value= isFormValid()
        }
        addSource(examTimeErrorMsg){
            value= isFormValid()
        }
        addSource(examKeyErrorMsg){
            value= isFormValid()
        }
        addSource(questions) {
            value= isFormValid()
            if(questions.value.isNullOrEmpty()){
                questionsListError =  R.string.empty_questions_error
            }
        }
    }
    private val emptyObserver= { _:Boolean->}
    init {
        // to trigger live data
        isFormValid.observeForever(emptyObserver)
    }

    fun addQuestion(question: Question) {
        _questions.value = _questions.value?.toMutableList()?.apply { add(0, question) }
    }

    fun removeQuestion(index: Int) {
        _questions.value = _questions.value?.toMutableList()?.apply { removeAt(index) }
    }

    fun addOnlineExam() {
        _showErrors.value = true
        if (isFormValid.value != true) {
            if(questionsListError!=null){
                _errorMsgEvent.value = Event(questionsListError)
                questionsListError= null
            }
            return
        }

        val onlineExam = OnlineExam(
            id = "",
            subjectName = subjectsList.value!![examSubjectIndex.value!!],
            examDate = LocalDateTime.of(examDate.value, examTime.value),
            examDuration = Duration.ofMinutes(examDurationInMinutes.value?.toLong() ?: 0),
            numberOfQuestions = questions.value?.size ?: 0,
            examStatus = OnlineExamStatus.INACTIVE,
        )

        val completeOnlineExam= CompleteOnlineExam(
            onlineExam= onlineExam,
            questions = questions.value.orEmpty()
        )

        viewModelScope.launch {
            addOnlineExamsUseCase(completeOnlineExam)
            // TODO: complete this
            _examAddedEvent.postValue(Event(true))
        }
        Logger.d("$TAG, $onlineExam")
    }

    private fun isFormValid(): Boolean {
        return when {
            subjectErrorMsg.value != null ||
                    examDateErrorMsg.value != null ||
                    examTimeErrorMsg.value != null ||
                    examKeyErrorMsg.value != null ||
                    questions.value.isNullOrEmpty()-> false
            else -> true
        }
    }

    override fun onCleared() {
        isFormValid.removeObserver(emptyObserver)
        super.onCleared()
    }
}

val dummyQuestions = listOf(
    Question(
        id = "0",
        question = "ما هو الحيوان الذي إن تم قطع رجل من أرجله تنمو مجدداً؟",
        questionType = QuestionType.NORMAL,
    ),
    Question(
        id = "1",
        question = "النحلة ترفرف بجناحيها في الثانية الواحدة بمعدل….؟",
        options = listOf(
            "150 مرة",
            "250 مرة",
            "350 مرة",
        ),
        questionType = QuestionType.MULTI_CHOICE,
    ),
    Question(
        id = "2",
        question = "ان الشعور بالاكتئاب وتقلب المزاج مرتبط بنقص معدن المغنيسوم",
        questionType = QuestionType.CORRECTNESS,
    ),
)