package com.smart.resources.schools_app.features.rating

import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path




interface RatingDao{

    @GET("studentBehaviors")
    suspend fun fetchRating(): Response<List<RatingModel>>


}