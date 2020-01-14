package com.smart.resources.schools_app.features.rating

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smart.resources.schools_app.core.helpers.BackendHelper
import com.smart.resources.schools_app.core.myTypes.MyResult
import com.smart.resources.schools_app.core.myTypes.toMyResult
import com.smart.resources.schools_app.features.profile.StudentInfoModel
import com.smart.resources.schools_app.features.students.Student
import kotlinx.coroutines.*

typealias RatingResult1= MyResult<List<Student>>

class AddRatingViewModel : ViewModel() {
    private val rating: MutableLiveData<RatingResult1>
            by lazy { MutableLiveData<RatingResult1>() }

    fun getRatings(classId: Int):
            LiveData<RatingResult1> {
        fetchRatings(classId)

        return rating
    }

    private fun fetchRatings(classId:Int ){
        viewModelScope.launch {
            val result = GlobalScope.async { BackendHelper
                .studentDao.getStudentsByClass(classId.toString()) }.toMyResult()
            rating.value = result
        }
    }
}