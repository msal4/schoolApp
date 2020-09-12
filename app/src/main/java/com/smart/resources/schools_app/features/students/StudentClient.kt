package com.smart.resources.schools_app.features.students

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface StudentClient {
    @GET("studentsByClass/{id}")
    suspend fun getStudentsByClass(@Path("id") classId: String): Response<List<StudentWithMark>>
}