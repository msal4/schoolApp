package com.smart.resources.schools_app.features.notification

import retrofit2.Response
import retrofit2.http.GET

interface NotificationsDao{


    @GET("studentNotifications")
    suspend fun fetchNotifications(): Response<List<NotificationModel>>

    @GET("sectionNotifications")
    suspend fun fetchSectionNotifications(): Response<List<NotificationModel>>
}