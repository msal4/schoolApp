package com.smart.resources.schools_app.features.exam

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smart.resources.schools_app.core.util.*
import kotlinx.coroutines.*

typealias notificationsResult= MyResult<List<ExamModel>>

class ExamViewModel : ViewModel() {
    private val exams: MutableLiveData<notificationsResult>
            by lazy { MutableLiveData<notificationsResult>() }

    fun getExams():
            LiveData<notificationsResult> {
        fetchExams()

        return exams
    }


    private val examsDao: ExamDao = BackendHelper
        .retrofitWithAuth
        .create(ExamDao::class.java)


    fun fetchExams(){
        viewModelScope.launch {
            val result = GlobalScope.async { examsDao.fetchExams() }.toMyResult()
            exams.value = result
        }
    }
}