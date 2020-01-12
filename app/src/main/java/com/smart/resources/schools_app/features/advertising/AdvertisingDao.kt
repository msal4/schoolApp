package com.smart.resources.schools_app.features.advertising

import retrofit2.Response
import retrofit2.http.GET

interface AdvertisingDao {
    @GET("advertisements")
    suspend fun fetchAdvertisements(): Response<List<AdvertisingModel>>


}
