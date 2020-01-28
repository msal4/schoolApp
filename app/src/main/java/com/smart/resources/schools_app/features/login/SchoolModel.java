package com.smart.resources.schools_app.features.login;

public class SchoolModel {


    private String title, url;

    public SchoolModel(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }
}
