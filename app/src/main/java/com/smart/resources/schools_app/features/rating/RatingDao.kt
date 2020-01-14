package com.smart.resources.schools_app.features.rating

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface RatingDao{
    @GET("studentBehaviors")
    suspend fun fetchRating(): Response<List<AddRatingModel>>


    @POST("multiBehavior")
    suspend fun addRatings(@Body addRatingModels: List<AddRatingModel>): Response<Unit>
}