package com.smart.resources.schools_app.features.subject

import com.smart.resources.schools_app.core.myTypes.MyResult
import retrofit2.http.GET

interface SubjectClient {
    @GET("subjects")
    suspend fun getSubjects()
            : MyResult<List<Subject>>
}