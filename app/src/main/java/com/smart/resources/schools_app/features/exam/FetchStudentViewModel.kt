package com.smart.resources.schools_app.features.exam

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smart.resources.schools_app.core.helpers.BackendHelper
import com.smart.resources.schools_app.core.myTypes.MyResult
import com.smart.resources.schools_app.core.myTypes.toMyResult
import com.smart.resources.schools_app.features.students.Student
import kotlinx.coroutines.*

typealias notificationsResult3= MyResult<List<Student>>

class FetchStudentViewModel : ViewModel() {
    private val exams: MutableLiveData<notificationsResult3>
            by lazy { MutableLiveData<notificationsResult3>()
            }



    fun getStudents(examId: Int):
            LiveData<notificationsResult3> {

        fetchStudents(examId)
        return exams
    }


    fun fetchStudents(examId:Int){
        viewModelScope.launch {
            val result = GlobalScope.async { BackendHelper.examDao.getResultsByExam(examId.toString()) }.toMyResult()
            exams.value = result
        }
    }

}