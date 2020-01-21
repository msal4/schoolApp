package com.smart.resources.schools_app.features.profile;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {
    @PrimaryKey
    public int uid;
    @ColumnInfo(name = "access_token")
    public String accessToken;
    @ColumnInfo(name = "img")
    public String img;
    @ColumnInfo(name = "username")
    public String username;
    @ColumnInfo(name = "user_type")
    public int userType;


    public User(int uid,String accessToken, String img, String username, int userType) {
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
