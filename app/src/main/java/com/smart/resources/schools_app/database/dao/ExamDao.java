package com.smart.resources.schools_app.database.dao;

import com.smart.resources.schools_app.database.model.ExamModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ExamDao {

    @GET("exams")
    Call<List<ExamModel>> fetchExam();
}
