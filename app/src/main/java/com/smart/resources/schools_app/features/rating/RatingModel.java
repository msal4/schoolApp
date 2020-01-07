package com.smart.resources.schools_app.features.rating;

import org.threeten.bp.LocalDateTime;

public class RatingModel {

    private int idLibrary;
    private String className,attachment;
    private String title;

    private LocalDateTime addDate;


    public int getIdLibrary() {
        return idLibrary;
    }

    public String getClassName() {
        return className;
    }

    public String getAttachment() {
        return attachment;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getAddDate() {
        return addDate;
    }
}
