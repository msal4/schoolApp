package com.smart.resources.schools_app.features.schedule

import retrofit2.Response
import retrofit2.http.GET

interface ScheduleDao{
    @GET("sectionTableA")
    suspend fun fetchSchedule(): Response<List<List<String?>>>
}