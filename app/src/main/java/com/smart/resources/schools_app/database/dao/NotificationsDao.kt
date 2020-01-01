package com.smart.resources.schools_app.database.dao

import com.smart.resources.schools_app.database.model.LibraryModel
import com.smart.resources.schools_app.database.model.NotificationsModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path




interface NotificationsDao{


    @GET("notifications")
    suspend fun fetchNotifications(): Response<List<NotificationsModel>>

}