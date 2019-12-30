package com.smart.resources.schools_app.database.dao;

import com.smart.resources.schools_app.database.model.HomeworkModel;
import com.smart.resources.schools_app.database.model.LibraryModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface LibraryDao {

    @GET("library")
    Call<List<LibraryModel>> fetchLib();
}
