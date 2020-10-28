package com.smart.resources.schools_app.features.advertising

import com.smart.resources.schools_app.core.myTypes.MyResult
import retrofit2.http.GET

interface AdvertisingClient {
    @GET("advertisements")
    suspend fun fetchAdvertisements(): MyResult<List<AdvertisingModel>>


}
