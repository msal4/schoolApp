package com.smart.resources.schools_app.database.dao

import com.smart.resources.schools_app.database.model.LibraryModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface LibraryDao {
    @GET("classLibrary")
    suspend fun fetchLib(): Response<List<LibraryModel>>
}