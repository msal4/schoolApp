package com.smart.resources.schools_app.features.addLecture

import androidx.lifecycle.*
import com.smart.resources.schools_app.core.myTypes.Success
import com.smart.resources.schools_app.core.network.RetrofitHelper
import com.smart.resources.schools_app.features.subject.Subject
import kotlinx.coroutines.launch
import timber.log.Timber

class AddLectureViewModel: ViewModel() {
    val _subjects = MutableLiveData<List<Subject>>()
    val subjects: LiveData<List<Subject>>
    get() {
        return _subjects
    }
    val subjectErrMsg = MutableLiveData<String>()
    val lectureErrMsg = MutableLiveData<String>()
    val lectureSubjectErrMsg = MutableLiveData<String>()
    val classErrMsg = MutableLiveData<String>()
    val lectureUrlErrMsg = MutableLiveData<String>()


    fun fetchSubjects(className: String) {
        viewModelScope.launch {
           when (val res = RetrofitHelper.subjectClient.getClassSubjects(className)) {
                is Success -> {
                    Timber.d("---------------> $className: ${res.data}")
                    _subjects.postValue(res.data)
                }
                else -> {}
            }
        }
    }
}