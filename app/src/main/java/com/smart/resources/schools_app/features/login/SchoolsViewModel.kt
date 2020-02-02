package com.smart.resources.schools_app.features.login

import android.app.Application
import androidx.lifecycle.*
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.helpers.BackendHelper
import com.smart.resources.schools_app.core.myTypes.*
import com.smart.resources.schools_app.features.schools.SchoolModel
import kotlinx.coroutines.*

typealias schoolResult= MyResult<List<SchoolModel>>
class SchoolsViewModel(application: Application) : AndroidViewModel(application), CanLogout {

    val listState = ListState()

    private val schools: MutableLiveData<schoolResult>
            by lazy { MutableLiveData<schoolResult>() }

    fun getSchools():
            LiveData<schoolResult> {
        fetchSchools()

        return schools
    }


    private fun fetchSchools(){

        viewModelScope.launch {
            val result = GlobalScope.async { BackendHelper.accountDao.getSchools() }.toMyResult()
            schools.value = result

        }
    }
}