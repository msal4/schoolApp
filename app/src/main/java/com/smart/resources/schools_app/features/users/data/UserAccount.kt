package com.smart.resources.schools_app.features.users.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class UserAccount(
    @PrimaryKey
    val uid: String,
    val accessToken: String,
    var img: String,
    val username: String,
    val userType: Int
) {
    val originalId: String
        get() = uid.substring(1)
    val isStudent: Boolean
        get() = userType == 0

    override fun toString(): String {
        return "User{" +
                "uid='" + uid + '\'' +
                ", accessToken='" + accessToken + '\'' +
                ", img='" + img + '\'' +
                ", username='" + username + '\'' +
                ", userType=" + userType +
                '}'
    }
}