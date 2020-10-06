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
import com.smart.resources.schools_app.core.typeConverters.room.OnlineExamStatus
import com.smart.resources.schools_app.features.onlineExam.domain.model.onlineExam.OnlineExam
import com.smart.resources.schools_app.features.onlineExam.domain.usecase.IAddOnlineExamUseCase
import com.smart.resources.schools_app.features.onlineExam.domain.usecase.IGetOnlineExamsUseCase
import com.smart.resources.schools_app.features.onlineExam.domain.usecase.IRemoveOnlineExamUseCase
import com.smart.resources.schools_app.features.users.domain.usecase.IGetCurrentUserTypeUseCase
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.threeten.bp.Duration
import org.threeten.bp.LocalDateTime

class OnlineExamViewModel @ViewModelInject constructor(
    application: Application,
    private val getOnlineExamsUseCase: IGetOnlineExamsUseCase,
    private val removeOnlineExamUseCase: IRemoveOnlineExamUseCase,
    private val addOnlineExamUseCase: IAddOnlineExamUseCase, // TODO: remove this
    private val getCurrentUserTypeUseCase: IGetCurrentUserTypeUseCase
) : AndroidViewModel(application) {

    private val c = application.applicationContext

    val listState = ListState()
    private val userType by lazy {
        getCurrentUserTypeUseCase()
    }
    val isTeacher: Boolean
        get() {
            return userType == UserType.TEACHER
        }

    private val _deleteFailedEvent = MutableLiveData<Event<Pair<Int, Int>>>()
    val deleteFailedEvent: LiveData<Event<Pair<Int, Int>>> = _deleteFailedEvent

    // TODO: remove this
    init {
//        viewModelScope.launch {
//           addOnlineExamUseCase(
//               dummyOnlineExams[0]
//           )
//           delay(2000)
//           addOnlineExamUseCase(
//               dummyOnlineExams[2]
//           )
//           delay(5000)
//            addOnlineExamUseCase(
//                *dummyOnlineExams.map {
//                    CompleteOnlineExam(
//                        addOnlineExam = it,
//                        questions = dummyQuestions,
//                    )
//                }.toTypedArray()
//            )
//        }
    }

    private val examsFlow by lazy {
        getOnlineExamsUseCase()
    }
    val onlineExams: LiveData<List<OnlineExam>> = examsFlow.map {
        if (it.data.isNullOrEmpty()) {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    listState.setBodyError(R.string.no_exams.toString(c))
                }
                Resource.Status.ERROR -> listState.setBodyError(it.message.toString())
                Resource.Status.LOADING -> listState.setLoading(true)
            }
        }else{
            listState.setLoading(false)
        }

        it.data ?: listOf()
    }.asLiveData(context = viewModelScope.coroutineContext)

    fun removeExam(position: Int) {
        onlineExams.value?.get(position)?.let {
            viewModelScope.launch {
                val res= removeOnlineExamUseCase(it.id)
                if(res is ApiErrorResponse){
                    Logger.e( res.combinedMessage)
                    val errorMessageId = if(res.statusCode == 0) R.string.connection_error else R.string.delete_failed
                    _deleteFailedEvent.postValue(Event(Pair(position, errorMessageId)))
                }
            }
        }
    }
}

val dummyOnlineExams = listOf(
    OnlineExam(
        "0",
        "رياضيات",
        LocalDateTime.now(),
        Duration.ofMinutes(150),
        6,
        OnlineExamStatus.ACTIVE,
    ),
    OnlineExam(
        "1",
        "اللغة العربية",
        LocalDateTime.now(),
        Duration.ofMinutes(500),
        15,
        OnlineExamStatus.COMPLETED,
    ),
    OnlineExam(
        "2",
        "انكليزي",
        LocalDateTime.now(),
        Duration.ofMinutes(300),
        30,
        OnlineExamStatus.INACTIVE,
    ),
    OnlineExam(
        "3",
        "فيزياء",
        LocalDateTime.now(),
        Duration.ofMinutes(50),
        60,
        OnlineExamStatus.COMPLETED,
    ),
)