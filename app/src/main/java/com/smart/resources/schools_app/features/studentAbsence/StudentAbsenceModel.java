package com.smart.resources.schools_app.features.studentAbsence;
import com.google.gson.annotations.SerializedName;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;

public class StudentAbsenceModel {

    private int idAbsence;

    public int getIdAbsence() {
        return idAbsence;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public int getStudentId() {
        return studentId;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public LocalDateTime getAbsenceDate() {
        return absenceDate;
    }

    private String subjectName;

    private int studentId;

    private int teacherId;

    private LocalDateTime absenceDate;


}