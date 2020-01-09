package com.smart.resources.schools_app.core.util

import androidx.lifecycle.MutableLiveData

data class LoginException(
    val phoneNumberMsg: MutableLiveData<String> = MutableLiveData(),
    val passwordMsg: MutableLiveData<String> = MutableLiveData(),
    val msg: MutableLiveData<String> = MutableLiveData()
)


data class AddHomeworkException(
    val subjectNameMsg: MutableLiveData<String> = MutableLiveData(),
    val AssignmentNameMsg: MutableLiveData<String> = MutableLiveData(),
    val msg: MutableLiveData<String> = MutableLiveData()
)