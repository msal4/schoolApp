package com.smart.resources.schools_app.features.rating

import retrofit2.Response
import retrofit2.http.GET


interface RatingDao{
    @GET("studentBehaviors")
    suspend fun fetchRating(): Response<List<RatingModel>>


    @GET("studentBehaviors\\")
    suspend fun fetchStudents(): Response<List<RatingModel>>

}