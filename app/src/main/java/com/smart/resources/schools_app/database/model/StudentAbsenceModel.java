package com.smart.resources.schools_app.database.model;
import com.google.gson.annotations.SerializedName;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;

public class StudentAbsenceModel {

    @SerializedName("absence_id")
    private int absenceId;

    @SerializedName("subject_name")
    private String subjectName;

    @SerializedName("student_id")
    private int studentId;

    @SerializedName("teacher_id")
    private int teacherId;

    @SerializedName("absence_date")
    private LocalDateTime absenceDate;

    public int getAbsenceId(){
        return absenceId;
    }

    public String getSubjectName(){
        return subjectName;
    }

    public int getStudentId(){
        return studentId;
    }

    public int getTeacherId(){
        return teacherId;
    }

    public LocalDateTime getAbsenceDate(){
        return absenceDate;
    }
}