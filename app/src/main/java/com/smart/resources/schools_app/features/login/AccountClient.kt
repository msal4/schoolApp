package com.smart.resources.schools_app.features.login

import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


interface AccountClient{

    @POST("studentLogin")
    suspend fun loginStudent(@Body body: HashMap<String, String>): Response<JsonObject>

    @POST("teacherLogin")
    suspend fun loginTeacher(@Body body: HashMap<String, String>): Response<JsonObject>

}