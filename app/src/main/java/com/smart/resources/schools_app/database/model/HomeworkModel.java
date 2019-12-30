package com.smart.resources.schools_app.database.model;
import com.google.gson.annotations.SerializedName;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;

public class HomeworkModel {

    private String note;
    private LocalDateTime date;
    @SerializedName("assignment_name")
    private String assignmentName;
    @SerializedName("subject_name")
    private String subjectName;
    private int id;


    public String getNote() {
        return note;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getAssignmentName() {
        return assignmentName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public int getId() {
        return id;
    }
}
