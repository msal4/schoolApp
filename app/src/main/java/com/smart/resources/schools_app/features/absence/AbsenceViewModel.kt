package com.smart.resources.schools_app.features.absence

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smart.resources.schools_app.core.helpers.BackendHelper
import com.smart.resources.schools_app.core.myTypes.MyResult
import com.smart.resources.schools_app.core.myTypes.toMyResult
import kotlinx.coroutines.*

typealias Absence= MyResult<List<StudentAbsenceModel>>

class HomeworkViewModel : ViewModel() {
    private val absences: MutableLiveData<Absence>
            by lazy { MutableLiveData<Absence>() }

    fun getExams():
            LiveData<Absence> {
        fetchAbsences()

        return absences
    }

    private fun fetchAbsences(){
        viewModelScope.launch {
            val result = GlobalScope.async { BackendHelper.absenceDao.fetchStudentAbsence() }.toMyResult()
            absences.value = result
        }
    }
}