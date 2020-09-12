package com.smart.resources.schools_app.features.absence

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AbsenceClient {
    @GET("studentAbsence")
    suspend fun fetchStudentAbsence(): Response<List<StudentAbsenceModel>>

    @POST("addMultiAbsence")
    suspend fun addStudentAbsences(@Body postAbsenceModel: PostAbsenceModel): Response<Unit>
}