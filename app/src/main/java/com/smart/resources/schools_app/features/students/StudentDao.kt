package com.smart.resources.schools_app.features.students

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface StudentDao {
    @GET("studentsByClass/{id}")
    suspend fun getStudentsByClass(@Path("id") classId: String): Response<List<Student>>
}