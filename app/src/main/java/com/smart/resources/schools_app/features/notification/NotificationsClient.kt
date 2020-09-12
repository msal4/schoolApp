package com.smart.resources.schools_app.features.notification

import retrofit2.Response
import retrofit2.http.GET

interface NotificationsClient{


    @GET("studentNotifications")
    suspend fun fetchNotifications(): Response<List<NotificationModel>>

    @GET("sectionNotifications")
    suspend fun fetchSectionNotifications(): Response<List<NotificationModel>>

    @GET("teacherNotifications")
    suspend fun fetchTeacherNotifications(): Response<List<NotificationModel>>
}

