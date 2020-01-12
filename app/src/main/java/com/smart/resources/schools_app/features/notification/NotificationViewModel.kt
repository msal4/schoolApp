package com.smart.resources.schools_app.features.notification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smart.resources.schools_app.core.helpers.BackendHelper
import com.smart.resources.schools_app.core.helpers.SharedPrefHelper
import com.smart.resources.schools_app.core.myTypes.MyResult
import com.smart.resources.schools_app.core.myTypes.NotificationType
import com.smart.resources.schools_app.core.myTypes.UserType
import com.smart.resources.schools_app.core.myTypes.toMyResult
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

    val userType= SharedPrefHelper.instance?.userType


    fun fetchNotifications(notificationType: NotificationType){

        viewModelScope.launch {
            val result = GlobalScope.async {
                if(userType == UserType.STUDENT)getStudentNotifications(notificationType)
                else  BackendHelper.notificationDao.fetchTeacherNotifications()
            }.toMyResult()
            notifications.value = result
        }
    }

    private suspend fun getStudentNotifications(notificationType: NotificationType) =
        with(BackendHelper.notificationDao){
            if (notificationType == NotificationType.STUDENT) fetchNotifications()
            else fetchSectionNotifications()
        }

}