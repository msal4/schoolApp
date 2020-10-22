package com.smart.resources.schools_app.features.onlineExam.domain.viewModel

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.hadiyarajesh.flower.ApiErrorResponse
import com.hadiyarajesh.flower.Resource
import com.haytham.coder.extensions.toString
import com.orhanobut.logger.Logger
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.extentions.combinedMessage
import com.smart.resources.schools_app.core.myTypes.Event
import com.smart.resources.schools_app.core.myTypes.ListState
import com.smart.resources.schools_app.core.myTypes.UserType
import com.smart.resources.schools_app.features.onlineExam.domain.model.onlineExam.OnlineExam
import com.smart.resources.schools_app.features.onlineExam.domain.usecase.IActivateOnlineExamUseCase
import com.smart.resources.schools_app.features.onlineExam.domain.usecase.IFinishOnlineExamUseCase
import com.smart.resources.schools_app.features.onlineExam.domain.usecase.IGetUserOnlineExamsUseCase
import com.smart.resources.schools_app.features.onlineExam.domain.usecase.IRemoveOnlineExamUseCase
import com.smart.resources.schools_app.features.users.domain.usecase.IGetCurrentUserTypeUseCase
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class OnlineExamViewModel @ViewModelInject constructor(
    application: Application,
    private val getUserOnlineExamsUseCase: IGetUserOnlineExamsUseCase,
    private val removeOnlineExamUseCase: IRemoveOnlineExamUseCase,
    private val getCurrentUserTypeUseCase: IGetCurrentUserTypeUseCase,
    private val activateOnlineExamUseCase: IActivateOnlineExamUseCase,
    private val finishOnlineExamUseCase: IFinishOnlineExamUseCase,
) : AndroidViewModel(application) {

    private val c = application.applicationContext
    val listState = ListState()
    private val userType by lazy { getCurrentUserTypeUseCase() }
    val isTeacher: Boolean get() = userType == UserType.TEACHER

    private val _deleteFailedEvent = MutableLiveData<Event<Pair<Int, Int>>>()
    val deleteFailedEvent: LiveData<Event<Pair<Int, Int>>> = _deleteFailedEvent

    private val _errorEvent = MutableLiveData<Event<Int>>()
    val errorEvent: LiveData<Event<Int>> = _errorEvent

    private val _actionInProgress = MutableLiveData<Boolean>()
    val actionInProgress: LiveData<Boolean> = _actionInProgress

    private val examsFlow by lazy { getUserOnlineExamsUseCase() }
    val onlineExams: LiveData<List<OnlineExam>> = examsFlow.map {
        if (it.data.isNullOrEmpty()) {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    listState.setBodyError(R.string.no_exams.toString(c))
                }
                // TODO: handle status code 404 for not found
                Resource.Status.ERROR -> listState.setBodyError(it.message.toString())
                Resource.Status.LOADING -> listState.setLoading(true)
            }
        } else {
            listState.setLoading(false)
        }

        it.data ?: listOf()
    }.asLiveData(context = viewModelScope.coroutineContext)


    fun removeExam(position: Int) {
        onlineExams.value?.get(position)?.let {
            viewModelScope.launch {
                val errorMsgId = deleteExam(it.id)
                if (errorMsgId != null) {
                    _deleteFailedEvent.postValue(Event(Pair(position, errorMsgId)))
                }
            }
        }
    }

    fun removeExam(examId: String) {
        viewModelScope.launch {
            val errorMsgId = deleteExam(examId)
            if (errorMsgId != null) {
                _errorEvent.postValue(Event(errorMsgId))
            }
        }
    }

    private suspend fun deleteExam(examId: String): Int? {
        _actionInProgress.postValue(true)
        val res = removeOnlineExamUseCase(examId)
        _actionInProgress.postValue(false)

        if (res is ApiErrorResponse) {
            Logger.e(res.combinedMessage)
            return if (res.statusCode == 0) R.string.connection_error else R.string.delete_failed
        }

        return null
    }

    fun activateExam(examId: String) {
        viewModelScope.launch {
            _actionInProgress.postValue(true)

            val res = activateOnlineExamUseCase(examId)
            if (res is ApiErrorResponse) {
                Logger.e(res.combinedMessage)
                val messageId =
                    if (res.statusCode == 0) R.string.connection_error else R.string.activation_failed
                _errorEvent.postValue(Event(messageId))
            }

            _actionInProgress.postValue(false)

        }
    }

    fun finishExam(examId: String) {
        viewModelScope.launch {
            _actionInProgress.postValue(true)
            val res = finishOnlineExamUseCase(examId)
            if (res is ApiErrorResponse) {
                Logger.e(res.combinedMessage)
                val messageId =
                    if (res.statusCode == 0) R.string.connection_error else R.string.finish_failed
                _errorEvent.postValue(Event(messageId))
            }

            _actionInProgress.postValue(false)
        }
    }
}
