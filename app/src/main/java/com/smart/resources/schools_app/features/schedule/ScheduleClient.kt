package com.smart.resources.schools_app.features.schedule

import com.smart.resources.schools_app.core.myTypes.MyResult
import retrofit2.http.GET

interface ScheduleClient{
    @GET("sectionTableA")
    suspend fun fetchSchedule(): MyResult<List<List<String?>>>
}