package com.smart.resources.schools_app.sharedUi

import androidx.lifecycle.MutableLiveData

class ListState(
    var isLoading: MutableLiveData<Boolean> = MutableLiveData(true),
    var bodyError:MutableLiveData<String> = MutableLiveData("")
){
    fun setLoading(isLoading: Boolean) {
        if(this.isLoading.value != isLoading)
         if(isLoading) setBodyError("")
        this.isLoading.value= isLoading
    }

    fun setBodyError(errorMsg: String) {
        if(bodyError.value != errorMsg)
        bodyError.value= errorMsg
    }
}
