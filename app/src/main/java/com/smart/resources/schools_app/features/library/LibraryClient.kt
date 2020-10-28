package com.smart.resources.schools_app.features.library

import com.smart.resources.schools_app.core.myTypes.MyResult
import retrofit2.http.GET

interface LibraryClient {
    @GET("classLibrary")
    suspend fun fetchLib(): MyResult<List<LibraryModel>>
}