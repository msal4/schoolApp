package com.smart.resources.schools_app.features.login

import android.app.Application
import androidx.lifecycle.*
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.helpers.BackendHelper
import com.smart.resources.schools_app.core.myTypes.*
import com.smart.resources.schools_app.features.absence.StudentAbsenceModel
import kotlinx.coroutines.*

typealias absenceResult= MyResult<List<SchoolModel>>
class SchoolsViewModel(application: Application) : AndroidViewModel(application), CanLogout {



    private val schools: MutableLiveData<absenceResult>
            by lazy { MutableLiveData<absenceResult>() }

    fun getSchools():
            LiveData<absenceResult> {
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