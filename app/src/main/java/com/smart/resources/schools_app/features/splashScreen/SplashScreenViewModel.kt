package com.smart.resources.schools_app.features.splashScreen

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smart.resources.schools_app.core.myTypes.Event
import com.smart.resources.schools_app.core.myTypes.UserType
import com.smart.resources.schools_app.features.users.domain.usecase.IGetCurrentUserTypeUseCase
import com.smart.resources.schools_app.features.users.domain.usecase.IGetFirstStudentLoginUseCase
import com.smart.resources.schools_app.features.users.domain.usecase.IGetFirstTeacherLoginUseCase
import com.smart.resources.schools_app.features.users.domain.usecase.IIsUserLoggedUseCase
import kotlinx.coroutines.launch

class SplashScreenViewModel @ViewModelInject constructor(
    private val isUserLoggedUseCase:IIsUserLoggedUseCase,
    private val getCurrentUserTypeUseCase:IGetCurrentUserTypeUseCase,
    private val getFirstStudentLoginUseCase: IGetFirstStudentLoginUseCase,
    private val getFirstTeacherLoginUseCase: IGetFirstTeacherLoginUseCase,
) : ViewModel() {
    private val _gotoHomeScreenEvent:MutableLiveData<Event<Boolean>> = MutableLiveData()
    private val _gotoOnBoardingScreenEvent:MutableLiveData<Event<Boolean>> = MutableLiveData()
    private val _continueSplashScreenEvent:MutableLiveData<Event<Boolean>> = MutableLiveData()

    val gotoHomeScreenEvent:LiveData<Event<Boolean>> = _gotoHomeScreenEvent
    val gotoOnBoardingScreenEvent:LiveData<Event<Boolean>> = _gotoOnBoardingScreenEvent
    val continueSplashScreenEvent:LiveData<Event<Boolean>> = _continueSplashScreenEvent

    init {
        selectNextScreenEvent()
    }

    private fun selectNextScreenEvent(){
        viewModelScope.launch {
            val fireEvent= Event(true)

            if(isUserLoggedUseCase()){
               val firstLogin=  when(getCurrentUserTypeUseCase()){
                    UserType.STUDENT -> getFirstStudentLoginUseCase()
                    UserType.TEACHER -> getFirstTeacherLoginUseCase()
                }

                if(firstLogin){
                    _gotoOnBoardingScreenEvent.postValue(fireEvent)
                }else{
                    _gotoHomeScreenEvent.postValue(fireEvent)
                }
            }else{
                _continueSplashScreenEvent.postValue(fireEvent)
            }
        }
    }
}