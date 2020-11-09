package com.smart.resources.schools_app.features.onlineExam.domain.viewModel

import android.app.Application
import android.os.CountDownTimer
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.hadiyarajesh.flower.ApiErrorResponse
import com.hadiyarajesh.flower.Resource
import com.haytham.coder.extensions.isNotNullOrEmpty
import com.haytham.coder.extensions.startCountDown
import com.haytham.coder.extensions.toString
import com.orhanobut.logger.Logger
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.extentions.TAG
import com.smart.resources.schools_app.core.myTypes.Event
import com.smart.resources.schools_app.core.myTypes.ListState
import com.smart.resources.schools_app.core.myTypes.UserType
import com.smart.resources.schools_app.core.typeConverters.room.OnlineExamStatus
import com.smart.resources.schools_app.features.onlineExam.domain.model.BaseAnswer
import com.smart.resources.schools_app.features.onlineExam.domain.model.BaseAnswerableQuestion
import com.smart.resources.schools_app.features.onlineExam.domain.model.onlineExam.OnlineExam
import com.smart.resources.schools_app.features.onlineExam.domain.usecase.*
import com.smart.resources.schools_app.features.onlineExam.presentation.fragments.ExamPaperFragment
import com.smart.resources.schools_app.features.users.domain.usecase.IGetCurrentUserIdUseCase
import com.smart.resources.schools_app.features.users.domain.usecase.IGetCurrentUserTypeUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.threeten.bp.Duration

typealias ListOfAnswerableQuestions = List<BaseAnswerableQuestion>

class ExamPaperViewModel @ViewModelInject constructor(
    private val getCurrentUserTypeUseCase: IGetCurrentUserTypeUseCase,
    private val getOnlineExamUseCase: IGetOnlineExamUseCase,
    private val syncOnlineExamUseCase: ISyncOnlineExamUseCase,
    private val saveAnswerLocallyUseCase: ISaveAnswerLocallyUseCase,
    private val sendAnswersUseCase: ISendAnswersUseCase,
    private val getUserIdUseCase: IGetCurrentUserIdUseCase,
    private val getExamQuestionsWithAnswersUseCase: IGetExamQuestionsWithAnswersUseCase,
    private val getExamQuestionsUseCase: IGetExamQuestionsUseCase,
    @Assisted private val savedStateHandle: SavedStateHandle,
    application: Application
) : AndroidViewModel(application) {

    private val c = application.applicationContext
    private val passedOnlineExam: OnlineExam get() = savedStateHandle.get(ExamPaperFragment.EXTRA_ONLINE_EXAM)!!
    private val passedStudentId: String? get() = savedStateHandle.get(ExamPaperFragment.EXTRA_STUDENT_ID)
    private val userType = liveData {
        emit(getCurrentUserTypeUseCase())
    }
    private var timer: CountDownTimer? = null

    private val _successEvent = MutableLiveData<Event<Int>>()
    private val _errorEvent = MutableLiveData<Event<Int>>()
    private val _sendingAnswers = MutableLiveData<Boolean>(false)
    private val studentId: LiveData<String?> = getStudentIdLiveData()

    val listState = ListState()
    val successEvent: LiveData<Event<Int>> = _successEvent
    val errorEvent: LiveData<Event<Int>> = _errorEvent
    val sendingAnswers: LiveData<Boolean> = _sendingAnswers
    val onlineExam = getExamLiveData()
    val readOnly = mapOnlineExamToReadOnly()
    val answerableQuestions = createAnswerableQuestionsLiveData()
    val questionsSolvedState = mapQuestionsToSolveState()
    val canSendAnswers: LiveData<Boolean> = mapReadOnlyAndQuestionsStateToCanSendAnswers()
    val remainingQuestionsText = mapQuestionsStateToRemainingQuestionsCount()

    private val _remainingDuration = mapReadOnlyAndExamToRemainingDuration()
    val remainingDuration: LiveData<Duration> = _remainingDuration

    private fun getStudentIdLiveData(): LiveData<String?> {
        return MediatorLiveData<String?>().apply {
            addSource(userType){
                    viewModelScope.launch {
                        val studentId = when {
                            passedStudentId != null -> passedStudentId
                            it == UserType.STUDENT -> getUserIdUseCase()
                            else -> null
                        }

                        postValue(studentId)
                    }
            }
        }
    }

    private fun getExamLiveData(): LiveData<OnlineExam> {
        return liveData {
            emit(passedOnlineExam)
            val examLiveData = getOnlineExamUseCase(passedOnlineExam.id)
                .map { it.data }
                .filterNotNull()
                .distinctUntilChanged()
                .asLiveData(viewModelScope.coroutineContext)
            emitSource(examLiveData)
        }
    }

    private fun mapOnlineExamToReadOnly(): LiveData<Boolean> {
        return MediatorLiveData<Boolean>().apply {
            addSource(onlineExam){
                !(it.examStatus == OnlineExamStatus.ACTIVE
                        && userType.value == UserType.STUDENT)
            }
            addSource(userType){
                !(onlineExam.value?.examStatus == OnlineExamStatus.ACTIVE
                        && it == UserType.STUDENT)
            }
        }
    }

    private fun mapReadOnlyAndExamToRemainingDuration(): MediatorLiveData<Duration> {
        return MediatorLiveData<Duration>().apply {
            value= Duration.ZERO

            addSource(readOnly) {
                onlineExam.value?.let { onlineExam ->
                    it?.let { updateRemainingTime(onlineExam, it) }
                }
            }
            addSource(onlineExam) {
                readOnly.value?.let { readOnly ->
                    it?.let { updateRemainingTime(it, readOnly) }
                }
            }
        }
    }

    private fun updateRemainingTime(onlineExam: OnlineExam, readOnly: Boolean) {
        timer?.cancel()
        if (readOnly) {
            _remainingDuration.value = onlineExam.examDuration
        } else {
            timer = onlineExam.remainingDuration.startCountDown(
                onTicked = {
                    _remainingDuration.value = it
                },
                intervalInMilli = 1000
            )
        }
    }

    private fun mapQuestionsToSolveState() =
        answerableQuestions
            .map {
                it.map { q ->
                    q.answer != null &&
                            q.answer!!.hasAnswerString
                }
            }

    private fun mapReadOnlyAndQuestionsStateToCanSendAnswers(): LiveData<Boolean> {
        return MediatorLiveData<Boolean>().apply {
            addSource(readOnly) {
                value = canSendAnswers()
            }
            addSource(questionsSolvedState) {
                value = canSendAnswers()
            }
        }
    }

    private fun canSendAnswers(): Boolean {
        val onlineExam = onlineExam.value ?: return false
        val readOnly: Boolean = readOnly.value ?: true
        val solvedQuestionsCount = questionsSolvedState.value.orEmpty().filter { it }.size

        return (solvedQuestionsCount >= onlineExam.numberOfRequiredQuestions && !readOnly)
    }

    private fun mapQuestionsStateToRemainingQuestionsCount(): LiveData<String> {
        return questionsSolvedState.map {
            String.format("%d/%d", it.filter { solved -> !solved }.size, it.size)
        }
    }

    private fun createAnswerableQuestionsLiveData(): LiveData<ListOfAnswerableQuestions> {
        return MediatorLiveData<ListOfAnswerableQuestions>().apply{
            addSource(studentId){userId->
                viewModelScope.launch {
                    getAnswerableQuestions(userId, readOnly.value, this@apply)
                }
            }

            addSource(readOnly){readOnly->
                viewModelScope.launch {
                    getAnswerableQuestions(studentId.value, readOnly, this@apply)
                }
            }
        }
    }

    private suspend fun getAnswerableQuestions(
        userId: String?,
        readOnly: Boolean?,
        mediatorLiveData: MediatorLiveData<ListOfAnswerableQuestions>
    ) {
        if (userId == null) {
            getExamQuestionsUseCase(passedOnlineExam.id).map {
                updateListState(it)
                it.data.orEmpty().map { question ->
                    BaseAnswerableQuestion.fromQuestionAnswer(question, null)
                }
            }

        } else {
            getExamQuestionsWithAnswersUseCase(
                passedOnlineExam.id,
                userId,
                passedOnlineExam.examStatus == OnlineExamStatus.COMPLETED || readOnly== true,
            ).map {
                updateListState(it)
                it.data.orEmpty()
            }

        }.collect {
            mediatorLiveData.postValue(it)
        }
    }

    private fun updateListState(it: Resource<List<Any>>) {
        if (it.data.isNotNullOrEmpty()) listState.setLoading(false)
        else when (it.status) {
            Resource.Status.SUCCESS -> {
                listState.setBodyError(R.string.no_questions.toString(c))
            }
            Resource.Status.ERROR -> {
                Logger.e(it.message.toString())
                listState.setBodyError(R.string.connection_error.toString(c))
            }
            Resource.Status.LOADING -> {
                listState.setLoading(true)
            }
        }
        Logger.d("$TAG: $it")
    }

    fun checkExamStatus() {
        onlineExam.value?.id?.let {
            viewModelScope.launch {
                Logger.d(syncOnlineExamUseCase(it).toString())
            }
        }
    }

    fun updateAnswer(answer: BaseAnswer, position: Int) {
        viewModelScope.launch {
            answerableQuestions.value?.getOrNull(position)?.id?.let {
                saveAnswerLocallyUseCase(answer, it)
            }
        }
    }

    fun sendAnswers() {
        viewModelScope.launch {
            _sendingAnswers.postValue(true)
            val questionsWithAnswers = answerableQuestions.value.orEmpty()
            val answers = questionsWithAnswers.mapNotNull { it.answer }
            val questionIds = questionsWithAnswers.map { it.question.id }

            when (val res = sendAnswersUseCase(answers, questionIds)) {
                is ApiErrorResponse -> {
                    Logger.e("$TAG: ${res.errorMessage}")
                    val errorMsgId = if (res.statusCode == 0) R.string.connection_error
                    else R.string.send_failed

                    _errorEvent.postValue(Event(errorMsgId))
                }
                else -> {
                    _successEvent.postValue(Event(R.string.answers_sent_successfully))
                }
            }

            _sendingAnswers.postValue(false)
        }
    }
}
