package com.smart.resources.schools_app.database.dao;

import com.smart.resources.schools_app.database.model.ExamModel;
import com.smart.resources.schools_app.database.model.StudentAbsenceModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface StudentAbsenceDao {

    @GET("studentAbsence")
    Call<List<StudentAbsenceModel>> fetchstudentAbsence();
}
