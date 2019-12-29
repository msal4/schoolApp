package com.smart.resources.schools_app.util

import androidx.lifecycle.MutableLiveData

data class LoginException(
    val phoneNumberMsg: MutableLiveData<String> = MutableLiveData(),
    val passwordMsg: MutableLiveData<String> = MutableLiveData(),
    val msg: MutableLiveData<String> = MutableLiveData()
)