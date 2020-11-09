package com.smart.resources.schools_app.features.onBoarding

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.smart.resources.schools_app.core.myTypes.Event
import com.smart.resources.schools_app.core.myTypes.UserType
import com.smart.resources.schools_app.features.users.domain.usecase.IGetCurrentUserTypeUseCase
import com.smart.resources.schools_app.features.users.domain.usecase.ISetNotFirstStudentLoginUseCase
import com.smart.resources.schools_app.features.users.domain.usecase.ISetNotFirstTeacherLoginUseCase
import kotlinx.coroutines.launch

class OnBoardingViewModel @ViewModelInject constructor(
    private val getUserTypeUseCase: IGetCurrentUserTypeUseCase,
    private val setNotFirstStudentLoginUseCase: ISetNotFirstStudentLoginUseCase,
    private val setNotFirstTeacherLoginUseCase: ISetNotFirstTeacherLoginUseCase,
    ) : ViewModel() {
    val userType = liveData {
        emit(getUserTypeUseCase())
    }
    val btnAnimationDuration= 300L

    private val _onBoardingFinishedEvent= MutableLiveData<Event<Boolean>>()
    val onBoardingFinishedEvent:LiveData<Event<Boolean>> = _onBoardingFinishedEvent

    fun finishOnBoarding(){
        viewModelScope.launch {
            when(userType.value){
                UserType.STUDENT -> {
                    setNotFirstStudentLoginUseCase()
                }
                UserType.TEACHER -> {
                    setNotFirstTeacherLoginUseCase()
                }
            }
            _onBoardingFinishedEvent.postValue(Event(true))
        }
    }
}