package com.smart.resources.schools_app.core.myTypes

import androidx.annotation.StringRes
import androidx.lifecycle.MutableLiveData

data class LoginException(
    val phoneNumberMsg: MutableLiveData<String> = MutableLiveData(),
    val passwordMsg: MutableLiveData<String> = MutableLiveData(),
    val msg: MutableLiveData<String> = MutableLiveData()
)


data class PostException(
    val subjectNameMsg: MutableLiveData<String> = MutableLiveData(),
    val typeMsg: MutableLiveData<String> = MutableLiveData(),
    val dateMsg:  MutableLiveData<String> = MutableLiveData(),
    val sectionMsg:  MutableLiveData<String> = MutableLiveData()
)


data class AddAbsenceException(
    val sectionAndClassesMsg: MutableLiveData<String> = MutableLiveData(),
    val subjectMsg: MutableLiveData<String> = MutableLiveData()
){
    fun safeReset(){
        if(!sectionAndClassesMsg.value.isNullOrBlank()) sectionAndClassesMsg.value= ""
        if(!subjectMsg.value.isNullOrBlank()) subjectMsg.value= ""
    }
}