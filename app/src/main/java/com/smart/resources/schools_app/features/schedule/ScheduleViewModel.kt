package com.smart.resources.schools_app.features.schedule

import android.app.Application
import androidx.lifecycle.*
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.utils.RetrofitHelper
import com.smart.resources.schools_app.core.myTypes.*
import com.smart.resources.schools_app.features.login.CanLogout
import kotlinx.coroutines.*


class ScheduleViewModel(application: Application) : AndroidViewModel(application) {
    private val c= application.applicationContext
    val listState = ListState()
    private val schedule: MutableLiveData<List<List<String?>>>
            by lazy { MutableLiveData<List<List<String?>>>() }

    fun getSchedule():
            LiveData<List<List<String?>>> {
        fetchSchedule()

        return schedule
    }

    private fun fetchSchedule(){
        viewModelScope.launch {
            listState.apply {
                setLoading(true)

                val result = GlobalScope.async { RetrofitHelper.scheduleClient.fetchSchedule() }.toMyResult()
                when (result) {
                    is Success -> {
                        if (result.data.isNullOrEmpty())
                            setBodyError(c.getString(R.string.no_advertisements))
                        else {
                            setLoading(false)
                            schedule.value = result.data
                        }

                    }
                    is ResponseError -> setBodyError(result.combinedMsg)
                    is ConnectionError -> setBodyError(c.getString(R.string.connection_error))
                }
            }
        }
    }
}