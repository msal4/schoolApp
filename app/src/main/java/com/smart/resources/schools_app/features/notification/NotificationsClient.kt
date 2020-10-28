package com.smart.resources.schools_app.features.notification

import com.smart.resources.schools_app.core.myTypes.MyResult
import retrofit2.http.GET

interface NotificationsClient{


    @GET("studentNotifications")
    suspend fun fetchNotifications(): MyResult<List<NotificationModel>>

    @GET("sectionNotifications")
    suspend fun fetchSectionNotifications(): MyResult<List<NotificationModel>>

    @GET("teacherNotifications")
    suspend fun fetchTeacherNotifications(): MyResult<List<NotificationModel>>
}

