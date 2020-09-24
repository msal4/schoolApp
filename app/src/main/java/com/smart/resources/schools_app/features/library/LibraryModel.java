package com.smart.resources.schools_app.features.library;
import com.google.gson.annotations.SerializedName;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;

public class LibraryModel {

    private int idLibrary;
    private String className,attachment;
    private String title;

    private LocalDate addDate;


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

    public LocalDate getAddDate() {
        return addDate;
    }
}
