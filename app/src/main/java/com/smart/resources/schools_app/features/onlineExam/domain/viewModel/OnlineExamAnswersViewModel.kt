package com.smart.resources.schools_app.features.onlineExam.domain.viewModel

import android.app.Application
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.hadiyarajesh.flower.ApiEmptyResponse
import com.hadiyarajesh.flower.ApiErrorResponse
import com.hadiyarajesh.flower.ApiSuccessResponse
import com.haytham.coder.extensions.isValidIndex
import com.haytham.coder.extensions.toString
import com.orhanobut.logger.Logger
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.extentions.combinedMessage
import com.smart.resources.schools_app.core.myTypes.ListState
import com.smart.resources.schools_app.features.onlineExam.domain.model.onlineExam.OnlineExam
import com.smart.resources.schools_app.features.onlineExam.presentation.fragments.OnlineExamAnswersFragment.Factory.EXTRA_ONLINE_EXAM
import com.smart.resources.schools_app.features.students.models.StudentWithAnswerStatus
import com.smart.resources.schools_app.features.students.usecases.IGetStudentsWithAnswerStatus
import com.smart.resources.schools_app.features.users.domain.usecase.IGetCurrentTeacherModelUseCase
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

class OnlineExamAnswersViewModel @ViewModelInject constructor(
    private val getCurrentTeacherModelUseCase: IGetCurrentTeacherModelUseCase,
    private val getStudentsWithAnswerStatus: IGetStudentsWithAnswerStatus,
    @Assisted private val savedStateHandle: SavedStateHandle,
    application: Application,
) : AndroidViewModel(application) {

    private val c = application.applicationContext
    val passedOnlineExam: OnlineExam get() = savedStateHandle.get(EXTRA_ONLINE_EXAM)!!
    val listState = ListState()
    private val classModels= liveData {
        emit( getCurrentTeacherModelUseCase()?.classesInfo.orEmpty())
    }
    val classes = classModels.map {
      it.map{ classModel -> classModel.getClassSection }
    }
    val selectedClassPos = MutableLiveData(-1)
    val students: LiveData<List<StudentWithAnswerStatus>> = selectedClassPos.asFlow()
        .distinctUntilChanged()
        .map {
        listState.setLoading(true)
        val studentsList = if (it != null && it.isValidIndex(classModels.value.orEmpty().size)) {
            getClassStudents(classModels.value?.getOrNull(0)?.classId?:"")
        } else emptyList()
        listState.setLoading(false)
        studentsList
    }.asLiveData(viewModelScope.coroutineContext)

    private suspend fun getClassStudents(classId: String): List<StudentWithAnswerStatus> {
        return when (val res = getStudentsWithAnswerStatus(passedOnlineExam.id ,classId)) {
            is ApiEmptyResponse -> {
                listState.setBodyError(R.string.no_students.toString(c))
                emptyList()
            }
            is ApiSuccessResponse -> {
                if (res.body.isNullOrEmpty()) {
                    listState.setBodyError(R.string.no_students.toString(c))
                }
                res.body.orEmpty()
            }
            is ApiErrorResponse -> {
                Logger.e(res.combinedMessage)
                listState.setBodyError(R.string.connection_error.toString(c))
                emptyList()
            }
        }
    }
}
