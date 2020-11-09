package com.smart.resources.schools_app.features.users.presentation

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.smart.resources.schools_app.core.myTypes.Event
import com.smart.resources.schools_app.features.users.data.UserAccount
import com.smart.resources.schools_app.features.users.data.UserRepository
import com.smart.resources.schools_app.features.users.domain.usecase.IDeleteUserUseCase
import com.smart.resources.schools_app.features.users.domain.usecase.IGetCurrentUserIdUseCase
import kotlinx.coroutines.launch

class AccountsViewModel @ViewModelInject constructor(
    private val getCurrentUserIdUseCase: IGetCurrentUserIdUseCase,
    private val deleteUserUseCase: IDeleteUserUseCase,
    private val userRepository: UserRepository,
) : ViewModel() {

    val userAccounts: LiveData<List<UserAccount>> =
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