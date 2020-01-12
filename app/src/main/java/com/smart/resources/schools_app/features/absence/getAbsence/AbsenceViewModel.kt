package com.smart.resources.schools_app.features.absence.getAbsence

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.smart.resources.schools_app.features.absence.StudentAbsenceModel


class AbsenceViewModel : ViewModel() {
    private val absences: MutableLiveData<StudentAbsenceModel>
            by lazy { MutableLiveData<StudentAbsenceModel>() }

    fun getAbsence():
            LiveData<StudentAbsenceModel> {
        fetchAbsences()

        return absences
    }

    private fun fetchAbsences(){
//        viewModelScope.launch {
//            val result = GlobalScope.async { BackendHelper.absenceDao.fetchStudentAbsence() }.toMyResult()
//            absences.value = result
//        }
    }
}