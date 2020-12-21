package com.smart.resources.schools_app.features.subject

import com.smart.resources.schools_app.core.myTypes.MyResult
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryName

interface SubjectClient {
    @GET("subjects")
    suspend fun getSubjects()
            : MyResult<List<Subject>>

    @GET("subjects/{className}")
    suspend fun getClassSubjects(@Path("className") className: String)
            : MyResult<List<Subject>>
}