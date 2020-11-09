package com.smart.resources.schools_app.features.notification

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.myTypes.*
import com.smart.resources.schools_app.core.network.RetrofitHelper
import com.smart.resources.schools_app.features.users.domain.usecase.IGetCurrentUserTypeUseCase
import kotlinx.coroutines.launch

class NotificationViewModel @ViewModelInject constructor(
    application: Application,
    private val getCurrentUserTypeUseCase: IGetCurrentUserTypeUseCase
) : AndroidViewModel(application) {
    private val c = application.applicationContext
    val listState = ListState()

    val notificationType: MutableLiveData<NotificationType> = MutableLiveData(NotificationType.STUDENT)
    val userType = liveData {
        emit(getCurrentUserTypeUseCase())
    }

    val notifications: LiveData<List<NotificationModel>> =
        MediatorLiveData<List<NotificationModel>>().apply {
            addSource(notificationType) {
                viewModelScope.launch {
                    userType.value?.let { userType ->
                        postValue(fetchNotifications(userType, it))
                    }
                }
            }
            addSource(userType) {
                viewModelScope.launch {
                    notificationType.value?.let { notificationType ->
                        postValue(fetchNotifications(it, notificationType))
                    }
                }
            }
        }

    private suspend fun fetchNotifications(
        userType: UserType,
        notificationType: NotificationType
    ): List<NotificationModel> {
        listState.apply {
            setLoading(true)

            val result =
                if (userType == UserType.STUDENT) getStudentNotifications(notificationType)
                else RetrofitHelper.notificationClient.fetchTeacherNotifications()

            when (result) {
                is Success -> {
                    if (result.data.isNullOrEmpty())
                        setBodyError(c.getString(R.string.no_notifications))
                    else {
                        setLoading(false)
                        return result.data
                    }

                }
                is ResponseError -> setBodyError(result.combinedMsg)
                is ConnectionError -> setBodyError(c.getString(R.string.connection_error))
            }

            return emptyList()
        }

    }

    private suspend fun getStudentNotifications(notificationType: NotificationType) =
        with(RetrofitHelper.notificationClient) {
            if (notificationType == NotificationType.STUDENT) fetchNotifications()
            else fetchSectionNotifications()
        }

}