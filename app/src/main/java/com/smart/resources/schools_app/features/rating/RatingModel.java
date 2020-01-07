package com.smart.resources.schools_app.features.rating;

import org.threeten.bp.LocalDateTime;

public class RatingModel {
    private int idBehavior;
    private int rate;
    private int teacherId;
    private int studentId;
    private String note;
    private LocalDateTime date;

    public int getIdBehavior(){
        return idBehavior;
    }

    public int getRate(){
        return rate;
    }

    public int getTeacherId(){
        return teacherId;
    }

    public int getStudentId(){
        return studentId;
    }

    public String getNote(){
        return note;
    }

    public LocalDateTime getDate(){
        return date;
    }

}
