package com.smart.resources.schools_app.features.rating

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.myTypes.ConnectionError
import com.smart.resources.schools_app.core.myTypes.ListState
import com.smart.resources.schools_app.core.myTypes.ResponseError
import com.smart.resources.schools_app.core.myTypes.Success
import com.smart.resources.schools_app.core.network.RetrofitHelper
import kotlinx.coroutines.launch


class RatingViewModel(application: Application) : AndroidViewModel(application) {
    private val c= application.applicationContext
    val listState = ListState()

    private val rating: MutableLiveData<List<RatingModel>>
            by lazy { MutableLiveData<List<RatingModel>>() }

    fun getRatings():
            LiveData<List<RatingModel>> {
        fetchRatings()

        return rating
    }

    private fun fetchRatings() {
        viewModelScope.launch {
            listState.apply {
                setLoading(true)

                val result = RetrofitHelper.ratingClient.fetchRating()

                when (result) {
                    is Success -> {
                        if (result.data.isNullOrEmpty())
                            setBodyError(c.getString(R.string.no_rating))
                        else {
                            setLoading(false)
                            rating.value = result.data
                        }

                    }
                    is ResponseError -> setBodyError(result.combinedMsg)
                    is ConnectionError -> setBodyError(c.getString(R.string.connection_error))
                }
            }
        }
    }
}