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
import com.smart.resources.schools_app.features.onlineExam.presentation.fragments.ExamPaperFragment
import com.smart.resources.schools_app.features.onlineExam.presentation.fragments.OnlineExamAnswersFragment.Factory.EXTRA_ONLINE_EXAM
import com.smart.resources.schools_app.features.profile.ClassInfoModel
import com.smart.resources.schools_app.features.students.IGetClassStudentsUseCase
import com.smart.resources.schools_app.features.students.Student
import com.smart.resources.schools_app.features.users.domain.usecase.IGetCurrentTeacherModelUseCase
import kotlinx.coroutines.flow.map


// TODO: modify ApiResponse to
// - not used null body
// - to use object EmptyApiResponse
class OnlineExamAnswersViewModel @ViewModelInject constructor(
    private val getCurrentTeacherModelUseCase: IGetCurrentTeacherModelUseCase,
    private val getClassStudentsUseCase: IGetClassStudentsUseCase,
    @Assisted private val savedStateHandle: SavedStateHandle,
    application: Application,
) : AndroidViewModel(application) {

    private val c = application.applicationContext
    val passedOnlineExam: OnlineExam get() = savedStateHandle.get(EXTRA_ONLINE_EXAM)!!
    val listState = ListState()
    private val classModels: List<ClassInfoModel> by lazy {
        getCurrentTeacherModelUseCase()?.classesInfo.orEmpty()
    }
    val classes = liveData {
        val fullClassNames = classModels.map { it.getClassSection }
        emit(fullClassNames)
    }
    val selectedClassPos = MutableLiveData(-1)
    val students: LiveData<List<Student>> = selectedClassPos.asFlow().map {
        listState.setLoading(true)
        val studentsList = if (it != null && it.isValidIndex(classModels.size)) {
            getClassStudents(classModels[it].classId)
        } else emptyList()
        listState.setLoading(false)
        studentsList
    }.asLiveData(viewModelScope.coroutineContext)

    private suspend fun getClassStudents(classId: String): List<Student> {
        return when (val res = getClassStudentsUseCase(classId)) {
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


val dummyStudents: List<Student> = listOf(
    Student(
        id = "0",
        name = "student0",
    ),
    Student(
        id = "1",
        name = "student0",
    ),
    Student(
        id = "2",
        name = "student0",
    ),
    Student(
        id = "3",
        name = "student0",
    ),
)