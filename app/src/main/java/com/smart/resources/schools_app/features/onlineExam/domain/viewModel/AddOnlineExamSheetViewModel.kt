package com.smart.resources.schools_app.features.onlineExam.domain.viewModel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.smart.resources.schools_app.features.login.CanLogout
import com.smart.resources.schools_app.features.onlineExam.domain.usecase.IAddOnlineExamByKeyUseCase
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.myTypes.Event
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class AddOnlineExamSheetViewModel @ViewModelInject constructor(
    private val addOnlineExamByKeyUseCase: IAddOnlineExamByKeyUseCase,
) : ViewModel() {

    private val _examKeyError= MutableLiveData<Int?>()
    private val _isLoading = MutableLiveData<Boolean>(false)
    private val _onExamAdded = MutableLiveData<Event<Boolean>>()

    val examKey= MutableLiveData<String>()
    val examKeyError:LiveData<Int?> = _examKeyError
    val isLoading:LiveData<Boolean> = _isLoading
    val onExamAdded:LiveData<Event<Boolean>> = _onExamAdded


    fun addExam(){
        if(examKey.value.isNullOrBlank()){
            _examKeyError.value= R.string.empty_exam_key_error
            return
        }

        viewModelScope.launch {
            _isLoading.postValue(true)
            // TODO: handle result
            addOnlineExamByKeyUseCase(examKey.value?:"")

            delay(2000)
            _isLoading.postValue(false)
            delay(300)
            _onExamAdded.postValue(Event(true)) // TODO remove this

        }
    }
}
