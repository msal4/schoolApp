package com.smart.resources.schools_app.features.rating

import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path




interface RatingDao{
    companion object{
        private const val USERNAME_PATH= "username"
        private const val PASSWORD_PATH= "password"
    }

    @POST("studentLogin")
    suspend fun loginStudent(@Body body: HashMap<String, String>): Response<JsonObject>

}