package com.smart.resources.schools_app.features.homework

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smart.resources.schools_app.core.util.*
import kotlinx.coroutines.*

typealias HomeworkResult= MyResult<List<HomeworkModel>>

class HomeworkViewModel : ViewModel() {
    private val homework: MutableLiveData<HomeworkResult>
            by lazy { MutableLiveData<HomeworkResult>() }

    fun getExams():
            LiveData<HomeworkResult> {
        fetchHomework()

        return homework
    }


    private val homeworkDao: HomeworkDao = BackendHelper
        .retrofitWithAuth
        .create(HomeworkDao::class.java)


    private fun fetchHomework(){
        viewModelScope.launch {
            val result = GlobalScope.async { homeworkDao.fetchHomework() }.toMyResult()
            homework.value = result
        }
    }
}