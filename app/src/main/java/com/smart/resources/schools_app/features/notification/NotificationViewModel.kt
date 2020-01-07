package com.smart.resources.schools_app.features.notification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smart.resources.schools_app.core.util.*
import kotlinx.coroutines.*

typealias notificationsResult= MyResult<List<NotificationModel>>

class NotificationViewModel : ViewModel() {
    private val notifications: MutableLiveData<notificationsResult>
            by lazy { MutableLiveData<notificationsResult>() }

    fun getNotifications(notificationType: NotificationType):
            LiveData<notificationsResult> {
        fetchNotifications(notificationType)

        return notifications
    }


    private val notificationsDao: NotificationsDao = BackendHelper
        .retrofitWithAuth
        .create(NotificationsDao::class.java)


    fun fetchNotifications(notificationType: NotificationType){
        viewModelScope.launch {
            val result = GlobalScope.async { getNotificationsDeferred(notificationType) }.toMyResult()
            notifications.value = result
        }
    }

    private suspend fun getNotificationsDeferred(notificationType: NotificationType) =
        if (notificationType == NotificationType.STUDENT) notificationsDao.fetchNotifications()
        else notificationsDao.fetchSectionNotifications()
}