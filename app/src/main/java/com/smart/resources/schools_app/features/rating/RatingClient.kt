package com.smart.resources.schools_app.features.rating

import com.smart.resources.schools_app.core.myTypes.MyResult
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface RatingClient{
    @GET("studentBehaviors")
    suspend fun fetchRating(): MyResult<List<RatingModel>>


    @POST("multiBehavior")
    suspend fun addRatings(@Body ratingModels: List<RatingModel>): MyResult<Unit>
}