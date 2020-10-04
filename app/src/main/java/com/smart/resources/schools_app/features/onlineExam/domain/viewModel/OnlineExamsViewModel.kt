package com.smart.resources.schools_app.features.onlineExam.domain.viewModel

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.hadiyarajesh.flower.Resource
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.extentions.toString
import com.smart.resources.schools_app.core.myTypes.ListState
import com.smart.resources.schools_app.core.myTypes.UserType
import com.smart.resources.schools_app.core.typeConverters.room.OnlineExamStatus
import com.smart.resources.schools_app.features.login.CanLogout
import com.smart.resources.schools_app.features.onlineExam.domain.model.CompleteOnlineExam
import com.smart.resources.schools_app.features.onlineExam.domain.model.OnlineExam
import com.smart.resources.schools_app.features.onlineExam.domain.usecase.IAddOnlineExamsUseCase
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
    private val addOnlineExamsUseCase: IAddOnlineExamsUseCase, // TODO: remove this
    private val getCurrentUserTypeUseCase: IGetCurrentUserTypeUseCase
) : AndroidViewModel(application) {

    private val c = application.applicationContext
    val listState = ListState()

    private val userType  by lazy {
        getCurrentUserTypeUseCase()
    }

     val isTeacher:Boolean get()  {
        return userType == UserType.TEACHER
    }

    // TODO: remove this
    init {
        viewModelScope.launch {
//           addOnlineExamsUseCase(
//               dummyOnlineExams[0]
//           )
//           delay(2000)
//           addOnlineExamsUseCase(
//               dummyOnlineExams[2]
//           )
//           delay(5000)
            addOnlineExamsUseCase(
                *dummyOnlineExams.map {
                    CompleteOnlineExam(
                        onlineExam = it,
                        questions = dummyQuestions,
                    )
                }.toTypedArray()
            )
        }
    }

    // TODO: add unauthorized interceptor
    val onlineExams: LiveData<List<OnlineExam>> = getOnlineExamsUseCase().map {
        when (it.status) {
            Resource.Status.SUCCESS -> {
                if (it.data.isNullOrEmpty()) {
                    listState.setBodyError(R.string.no_exams.toString(c))
                } else {
                    listState.setLoading(false)
                }
            }
            Resource.Status.ERROR -> listState.setBodyError(it.message.toString())
            Resource.Status.LOADING -> listState.setLoading(it.data.isNullOrEmpty())
        }


        it.data ?: listOf()
    }.asLiveData(context = viewModelScope.coroutineContext)

    fun removeExam(position: Int) {
        onlineExams.value?.get(position)?.let {
            viewModelScope.launch {
                removeOnlineExamUseCase(it.id)
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