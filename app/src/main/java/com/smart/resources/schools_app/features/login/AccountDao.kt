package com.smart.resources.schools_app.features.login

import com.google.gson.JsonObject
import com.smart.resources.schools_app.features.schools.School
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface AccountDao{

    @POST("studentLogin")
    suspend fun loginStudent(@Body body: HashMap<String, String>): Response<JsonObject>

    @POST("teacherLogin")
    suspend fun loginTeacher(@Body body: HashMap<String, String>): Response<JsonObject>

    @GET("contacts.json")
    suspend fun getSchools(): Response<List<School>>
}