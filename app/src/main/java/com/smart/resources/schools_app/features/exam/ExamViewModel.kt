package com.smart.resources.schools_app.features.exam

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smart.resources.schools_app.core.helpers.BackendHelper
import com.smart.resources.schools_app.core.myTypes.MyResult
import com.smart.resources.schools_app.core.myTypes.toMyResult
import kotlinx.coroutines.*

typealias notificationsResult= MyResult<List<ExamModel>>

class ExamViewModel : ViewModel() {
    private val exams: MutableLiveData<notificationsResult>
            by lazy { MutableLiveData<notificationsResult>() }




    fun getExams():
            LiveData<notificationsResult> {

        return exams
    }

    fun fetchExams(){
        viewModelScope.launch {
            val result = GlobalScope.async { BackendHelper.examDao.fetchExams() }.toMyResult()
            exams.value = result
        }
    }

}