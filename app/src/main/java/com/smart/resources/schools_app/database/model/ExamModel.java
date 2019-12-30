package com.smart.resources.schools_app.database.model;

import com.google.gson.annotations.SerializedName;

import org.threeten.bp.LocalDateTime;

public class ExamModel {

    @SerializedName("id_exam")
    private int idExam;
    @SerializedName("subject_name")
    private String subjectName;
    private LocalDateTime date;
    private String note;
    private String type;
    @SerializedName("class_id")
    private int classId;
    @SerializedName("class_name")
    private String className;
    @SerializedName("teacher_id")
    private int teacherId;

    public int getIdExam(){
        return idExam;
    }

    public String getSubjectName(){
        return subjectName;
    }

    public LocalDateTime getDate(){
        return date;
    }

    public String getNote(){
        return note;
    }

    public String getType(){
        return type;
    }

    public int getClassId(){
        return classId;
    }

    public String getClassName(){
        return className;
    }

    public int getTeacherId(){
        return teacherId;
    }




    /*
    *
    *
    *
    * id_exam: 7,
subject_name: "new",
date: "2019-03-01T00:00:00.000Z",
note: "this is new exam",
type: "mid",
class_id: 1,
class_name: "",
teacher_id: 1
*
*
* */
}
