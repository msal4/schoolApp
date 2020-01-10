package com.smart.resources.schools_app.features.rating

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smart.resources.schools_app.core.helpers.BackendHelper
import com.smart.resources.schools_app.core.myTypes.MyResult
import com.smart.resources.schools_app.core.myTypes.toMyResult
import kotlinx.coroutines.*

typealias RatingResult= MyResult<List<RatingModel>>

class RatingViewModel : ViewModel() {
    private val homework: MutableLiveData<RatingResult>
            by lazy { MutableLiveData<RatingResult>() }

    fun getRatings():
            LiveData<RatingResult> {
        fetchRatings()

        return homework
    }

    private fun fetchRatings(){
        viewModelScope.launch {
            val result = GlobalScope.async { BackendHelper
                .ratingDao.fetchRating() }.toMyResult()
            homework.value = result
        }
    }
}