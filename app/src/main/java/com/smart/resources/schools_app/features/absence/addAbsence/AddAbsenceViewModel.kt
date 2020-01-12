package com.smart.resources.schools_app.features.absence.addAbsence

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orhanobut.logger.Logger
import com.smart.resources.schools_app.core.helpers.BackendHelper
import com.smart.resources.schools_app.core.myTypes.MyResult
import com.smart.resources.schools_app.core.myTypes.toMyResult
import com.smart.resources.schools_app.features.absence.PostAbsenceModel
import com.smart.resources.schools_app.features.students.Student
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

typealias studentResult= MyResult<List<Student>>

class AddAbsenceViewModel : ViewModel() {
    val postAbsenceModel=
        PostAbsenceModel()

    private val _students: MutableLiveData<studentResult> by lazy {
        MutableLiveData<studentResult>()
    }

    val students= _students

    fun getClassStudents(classId: String){
        Logger.i("absence model: $postAbsenceModel")

        viewModelScope.launch {
            val myRe = async {BackendHelper.studentDao.getStudentsByClass(classId)}
                .toMyResult()
            _students.value= myRe
        }
    }
}