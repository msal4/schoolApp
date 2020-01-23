package com.smart.resources.schools_app.features.profile;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity
public class User {
    @NotNull
    @PrimaryKey
    public String uid;
    public String accessToken;
    public String img;
    public String username;
    public int userType;


    public User(@NotNull String uid,String accessToken, String img, String username, int userType) {
        this.uid=uid;
        this.accessToken = accessToken;
        this.img = img;
        this.username = username;
        this.userType = userType;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid=" + uid +
                ", accessToken='" + accessToken + '\'' +
                ", img='" + img + '\'' +
                ", username='" + username + '\'' +
                ", userType=" + userType +
                '}';
    }
}
