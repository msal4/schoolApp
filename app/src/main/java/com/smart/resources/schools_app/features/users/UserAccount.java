package com.smart.resources.schools_app.features.users;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import org.jetbrains.annotations.NotNull;

@Entity()
public class UserAccount {
    @NotNull
    @PrimaryKey
    private String uid;
    private String accessToken;
    private String img;
    private String username;
    private int userType;

    @NotNull
    public String getUid() {
        return uid;
    }
    public String getOriginalId(){
        return uid.substring(1);
    }

    public void setUid(@NotNull String uid) {
        this.uid = uid;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getUserType() {
        return userType;
    }

    public Boolean isStudent() {
        return userType==0;
    }
    public void setUserType(int userType) {
        this.userType = userType;
    }


    public UserAccount(@NotNull String uid, String accessToken, String img, String username, int userType) {
        this.uid = uid;
        this.accessToken = accessToken;
        this.img = img;
        this.username = username;
        this.userType = userType;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid='" + uid + '\'' +
                ", accessToken='" + accessToken + '\'' +
                ", img='" + img + '\'' +
                ", username='" + username + '\'' +
                ", userType=" + userType +
                '}';
    }
}