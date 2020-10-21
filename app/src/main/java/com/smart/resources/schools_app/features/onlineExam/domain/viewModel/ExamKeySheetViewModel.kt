package com.smart.resources.schools_app.features.onlineExam.domain.viewModel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.hadiyarajesh.flower.Resource
import com.orhanobut.logger.Logger
import com.smart.resources.schools_app.features.onlineExam.domain.usecase.IIsExamKeyCorrectUseCase
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.myTypes.Event
import kotlinx.coroutines.launch
import com.smart.resources.schools_app.features.onlineExam.presentation.bottomSheets.ExamKeyBottomSheet
import kotlinx.coroutines.flow.collect

class ExamKeySheetViewModel @ViewModelInject constructor(
    private val isExamKeyCorrectUseCase: IIsExamKeyCorrectUseCase,
    @Assisted private val stateHandle: SavedStateHandle
) : ViewModel() {

    private val _examKeyError= MutableLiveData<Int?>()
    private val _isLoading = MutableLiveData<Boolean>(false)
    private val _onExamAdded = MutableLiveData<Event<Boolean>>()

    val examKey= MutableLiveData<String>()
    val examKeyError:LiveData<Int?> = _examKeyError
    val isLoading:LiveData<Boolean> = _isLoading
    val onExamAdded:LiveData<Event<Boolean>> = _onExamAdded
    val examId:String= stateHandle.get<String>(ExamKeyBottomSheet.EXTRA_EXAM_ID)?:""


    fun addExam(){
        if(examKey.value.isNullOrBlank()){
            _examKeyError.value= R.string.empty_key_error
            return
        }

        viewModelScope.launch {
            _isLoading.postValue(true)
            val resource= isExamKeyCorrectUseCase(examId, examKey.value?:"")
            resource.collect {
                when(it.status){
                    Resource.Status.SUCCESS -> {
                        _isLoading.postValue(false)
                        if(it.data== true){
                            _onExamAdded.postValue(Event(true))
                        }else{
                            _examKeyError.postValue(R.string.invalid_key)
                        }
                    }
                    Resource.Status.ERROR -> {
                        Logger.e(it.message.toString())
                        _examKeyError.postValue(R.string.unexpected_error)
                    }
                    Resource.Status.LOADING -> {

                    }
                }
            }
        }
    }
}
