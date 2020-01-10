package com.smart.resources.schools_app.features.schedule

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smart.resources.schools_app.core.helpers.BackendHelper
import com.smart.resources.schools_app.core.myTypes.MyResult
import com.smart.resources.schools_app.core.myTypes.toMyResult
import kotlinx.coroutines.*

typealias ScheduleResult= MyResult<List<List<String?>>>

class ScheduleViewModel : ViewModel() {
    private val schedule: MutableLiveData<ScheduleResult>
            by lazy { MutableLiveData<ScheduleResult>() }

    fun getSchedule():
            LiveData<ScheduleResult> {
        fetchSchedule()

        return schedule
    }

    private fun fetchSchedule(){
        viewModelScope.launch {
            val result = GlobalScope.async { BackendHelper.scheduleDao.fetchSchedule() }.toMyResult()
            schedule.value = result
        }
    }
}