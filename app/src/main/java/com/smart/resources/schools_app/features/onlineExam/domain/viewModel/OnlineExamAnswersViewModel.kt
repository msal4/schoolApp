package com.smart.resources.schools_app.features.onlineExam.domain.viewModel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.smart.resources.schools_app.core.myTypes.ListState
import com.smart.resources.schools_app.features.onlineExam.domain.usecase.GetOnlineExamsUseCase
import com.smart.resources.schools_app.features.profile.ClassInfoModel
import com.smart.resources.schools_app.features.students.Student
import com.smart.resources.schools_app.features.users.domain.usecase.GetCurrentTeacherModelUseCase
import com.smart.resources.schools_app.features.users.domain.usecase.IGetCurrentTeacherModelUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map

class OnlineExamAnswersViewModel @ViewModelInject constructor(
    private val getCurrentTeacherModelUseCase: IGetCurrentTeacherModelUseCase,
) : ViewModel(){

    val listState = ListState()

    private val classModels:List<ClassInfoModel> by lazy {
        getCurrentTeacherModelUseCase()?.classesInfo.orEmpty()
    }

    val classes = liveData{
        val fullClassNames= classModels.map { it.getClassSection }
        emit(fullClassNames)
    }
    val selectedClass:MutableLiveData<ClassInfoModel?> = MutableLiveData(null)

    val students:LiveData<List<Student>> = selectedClass.asFlow().map {
        listState.setLoading(true)
        // some operation
        delay(1000)
        val result= dummyStudents
        listState.setLoading(false)
        result
    }.asLiveData(viewModelScope.coroutineContext)

}


val dummyStudents:List<Student> = listOf(
    Student(
        id= "0",
        name = "student0",
    ),
    Student(
        id= "1",
        name = "student0",
    ),
    Student(
        id= "2",
        name = "student0",
    ),Student(
        id= "3",
        name = "student0",
    ),
)