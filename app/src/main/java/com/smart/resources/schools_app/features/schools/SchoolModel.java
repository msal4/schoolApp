package com.smart.resources.schools_app.features.schools;

public class SchoolModel {


    private String name, image;

    public SchoolModel(String title, String url) {
        this.name = title;
        this.image = url;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }
}
