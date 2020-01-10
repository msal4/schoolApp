package com.smart.resources.schools_app.features.homework

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smart.resources.schools_app.core.helpers.BackendHelper
import com.smart.resources.schools_app.core.myTypes.MyResult
import com.smart.resources.schools_app.core.myTypes.toMyResult
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


    private fun fetchHomework(){
        viewModelScope.launch {
            val result = GlobalScope.async { BackendHelper.homeworkDao.fetchHomework() }.toMyResult()
            homework.value = result
        }
    }

}