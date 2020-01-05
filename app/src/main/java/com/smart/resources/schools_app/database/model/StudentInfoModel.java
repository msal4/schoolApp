package com.smart.resources.schools_app.database.model;


import com.google.gson.Gson;
import com.smart.resources.schools_app.R;
import com.smart.resources.schools_app.util.BackendHelper;
import com.smart.resources.schools_app.util.SharedPrefHelper;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.Period;

import static com.smart.resources.schools_app.util.UtilsKt.decodeToken;

public class StudentInfoModel {
    private  static StudentInfoModel instance;

    public static StudentInfoModel getInstance(){
        if(instance== null) instance= fromToken();

        return instance;
    }


    private ClassInfoModel classInfo;
    private String idStudent,name,email,phone,gender;
    private LocalDateTime dob;


    public ClassInfoModel getClassInfoModel() {
        return classInfo;
    }

    public String getIdStudent() {
        return idStudent;
    }


    public String getAge() {
        return Period.between(dob.toLocalDate(), LocalDateTime.now().toLocalDate()).getYears()+" سنة";
    }


    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getGender() {
        return gender;
    }

    public LocalDateTime getDob() {
        return dob;
    }



    private  static StudentInfoModel fromToken(){
        try {
            String token = SharedPrefHelper.Companion.getInstance().getAccessToken();

            String body = decodeToken(token);
            Gson gson= BackendHelper.INSTANCE.getGson();
            return gson.fromJson(body,StudentInfoModel.class);

        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }
}
