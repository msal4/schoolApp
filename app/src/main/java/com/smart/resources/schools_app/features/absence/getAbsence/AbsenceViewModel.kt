package com.smart.resources.schools_app.features.absence.getAbsence

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smart.resources.schools_app.core.helpers.RetrofitHelper
import com.smart.resources.schools_app.core.myTypes.MyResult
import com.smart.resources.schools_app.core.myTypes.toMyResult
import com.smart.resources.schools_app.features.absence.StudentAbsenceModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


typealias absenceResult= MyResult<List<StudentAbsenceModel>>
class AbsenceViewModel : ViewModel() {
    private val absences: MutableLiveData<absenceResult>
            by lazy { MutableLiveData<absenceResult>() }

    fun getAbsence():
            LiveData<absenceResult> {
        fetchAbsences()

        return absences
    }

    private fun fetchAbsences(){
       viewModelScope.launch {
            val result = GlobalScope.async { RetrofitHelper.absenceDao.fetchStudentAbsence() }.toMyResult()
           absences.value = result
        }
    }
}