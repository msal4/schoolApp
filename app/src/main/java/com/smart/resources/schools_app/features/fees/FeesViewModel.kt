package com.smart.resources.schools_app.features.fees

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smart.resources.schools_app.core.myTypes.Success
import com.smart.resources.schools_app.core.network.RetrofitHelper
import kotlinx.coroutines.launch

class FeesViewModel: ViewModel() {
    private var fees = MutableLiveData(Fees(0, 0, 0, details = listOf()))

    fun getFees(): LiveData<Fees> {
        return fees
    }

    fun fetchFees() {
        viewModelScope.launch {
            when(val result = RetrofitHelper.feesClient.fetchFees()) {
                is Success -> {
                    fees.postValue(result.data)
                }

                else -> {

                }
            }
        }

    }
}
