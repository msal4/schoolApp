package com.smart.resources.schools_app.features.users;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.smart.resources.schools_app.features.schools.School;

import org.jetbrains.annotations.NotNull;

@Entity(
        foreignKeys = {
                @ForeignKey(
                        entity = School.class,
                        parentColumns = "schoolId",
                        childColumns = "schoolId",
                        onDelete = ForeignKey.CASCADE)
        },
        indices = {
                @Index("schoolId")
        }
)
public class User {
    @NotNull
    @PrimaryKey
    private String uid;
    private String accessToken;
    private String img;
    private String username;
    private int userType;
    private String schoolId;

    @NotNull
    public String getUid() {
        return uid;
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

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public User(@NotNull String uid, String accessToken, String img, String username, int userType, String schoolId) {
        this.uid = uid;
        this.accessToken = accessToken;
        this.img = img;
        this.username = username;
        this.userType = userType;
        this.schoolId = schoolId;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid='" + uid + '\'' +
                ", accessToken='" + accessToken + '\'' +
                ", img='" + img + '\'' +
                ", username='" + username + '\'' +
                ", userType=" + userType +
                ", schoolId=" + schoolId +
                '}';
    }
}
