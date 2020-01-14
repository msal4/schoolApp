package com.smart.resources.schools_app.features.exam

import android.app.Application
import androidx.lifecycle.*
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.helpers.BackendHelper
import com.smart.resources.schools_app.core.myTypes.*
import com.smart.resources.schools_app.features.students.Student
import kotlinx.coroutines.*


class AddMarkViewModel(application: Application) : AndroidViewModel(application) {
    private val c= application.applicationContext
    val listState = ListState()

    private val exams: MutableLiveData<List<Student>>
            by lazy { MutableLiveData<List<Student>>()
            }


    fun getStudents(examId: Int):
            LiveData<List<Student>> {

        fetchStudents(examId)
        return exams
    }


    fun fetchStudents(examId:Int){
        viewModelScope.launch {
            listState.apply {
                setLoading(true)

                val result = GlobalScope.async {  BackendHelper.examDao.getResultsByExam(examId.toString()) }.toMyResult()
                when (result) {
                    is Success -> {
                        if (result.data.isNullOrEmpty())
                            setBodyError(c.getString(R.string.no_advertisements))
                        else {
                            setLoading(false)
                            exams.value = result.data
                        }

                    }
                    is ResponseError -> setBodyError(result.combinedMsg)
                    is ConnectionError -> setBodyError(c.getString(R.string.connection_error))
                }
            }
        }
    }

}