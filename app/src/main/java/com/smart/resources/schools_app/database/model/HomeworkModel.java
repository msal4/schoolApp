package com.smart.resources.schools_app.database.model;
import com.google.gson.annotations.SerializedName;

public class HomeworkModel {

    private String note;
    private String date;
    @SerializedName("assignment_name")
    private String assignmentName;
    @SerializedName("subject_name")
    private String subjectName;
    private int id;


    public String getNote() {
        return note;
    }

    public String getDate() {
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
