package com.smart.resources.schools_app.database.dao

import com.smart.resources.schools_app.database.model.StudentAbsenceModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface StudentAbsenceDao {
    @GET("studentAbsence")
    suspend fun fetchStudentAbsence(): Response<List<StudentAbsenceModel>>
}