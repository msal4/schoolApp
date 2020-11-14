package com.smart.resources.schools_app.features.users.presentation

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.smart.resources.schools_app.core.myTypes.Event
import com.smart.resources.schools_app.features.users.data.model.Account
import com.smart.resources.schools_app.features.users.data.repository.UserRepository
import com.smart.resources.schools_app.features.users.domain.usecase.IDeleteUserUseCase
import com.smart.resources.schools_app.features.users.domain.usecase.IGetCurrentLocalUserIdUseCase
import kotlinx.coroutines.launch

class AccountsViewModel @ViewModelInject constructor(
    private val getCurrentUserIdUseCase: IGetCurrentLocalUserIdUseCase,
    private val deleteUserUseCase: IDeleteUserUseCase,
    private val userRepository: UserRepository,
) : ViewModel() {

    val accounts: LiveData<List<Account>> =
        userRepository
            .getUsers()
            .asLiveData(viewModelScope.coroutineContext)

    val currentUserId = liveData {
        emit(getCurrentUserIdUseCase())
    }

    private val _logoutEvent = MutableLiveData<Event<Boolean>>()
    val logoutEvent: LiveData<Event<Boolean>> = _logoutEvent

    fun deleteUser(userId:String) {
        viewModelScope.launch {
            deleteUserUseCase(userId)
            if (currentUserId.value == userId) {
                _logoutEvent.postValue(Event(true))
            }
        }
    }
}