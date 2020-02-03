package com.smart.resources.schools_app.features.users

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.smart.resources.schools_app.features.schools.School

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = School::class,
            parentColumns = arrayOf("schoolId"),
            childColumns = arrayOf("schoolId"),
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("schoolId")
    ]
)
class User(
    @field:PrimaryKey val uid: String,
    val accessToken: String,
    var img: String,
    val username: String,
    val userType: Int,
    val schoolId: String
) {
    override fun toString(): String {
        return "User{" +
                "uid=" + uid +
                ", accessToken='" + accessToken + '\'' +
                ", img='" + img + '\'' +
                ", username='" + username + '\'' +
                ", userType=" + userType +
                '}'
    }

}