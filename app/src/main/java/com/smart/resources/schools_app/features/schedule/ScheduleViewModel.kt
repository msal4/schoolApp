package com.smart.resources.schools_app.features.schedule

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smart.resources.schools_app.core.util.*
import kotlinx.coroutines.*

typealias ScheduleResult= MyResult<List<List<String?>>>

class ScheduleViewModel : ViewModel() {
    private val schedule: MutableLiveData<ScheduleResult>
            by lazy { MutableLiveData<ScheduleResult>() }

    fun getSchedule():
            LiveData<ScheduleResult> {
        fetchHomework()

        return schedule
    }


    private val scheduleDao: ScheduleDao = BackendHelper
        .retrofitWithAuth
        .create(ScheduleDao::class.java)


    private fun fetchHomework(){
        viewModelScope.launch {
            val result = GlobalScope.async { scheduleDao.fetchSchedule() }.toMyResult()
            schedule.value = result
        }
    }
}