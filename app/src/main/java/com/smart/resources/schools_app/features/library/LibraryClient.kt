package com.smart.resources.schools_app.features.library

import retrofit2.Response
import retrofit2.http.GET

interface LibraryClient {
    @GET("classLibrary")
    suspend fun fetchLib(): Response<List<LibraryModel>>
}