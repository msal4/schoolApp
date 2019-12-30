package com.smart.resources.schools_app.database.model;
import com.google.gson.annotations.SerializedName;

import org.threeten.bp.LocalDateTime;

public class LibraryModel {

    private int id;
    @SerializedName("class_name")
    private String className;
    private String title;
    @SerializedName("add_date")
    private LocalDateTime addDate;


    public int getId() {
        return id;
    }

    public String getClassName() {
        return className;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getAddDate() {
        return addDate;
    }
}
