package com.smart.resources.schools_app.features.absence

import retrofit2.Response
import retrofit2.http.GET

interface AbsenceDao {
    @GET("studentAbsence")
    suspend fun fetchStudentAbsence(): Response<List<StudentAbsenceModel>>
}