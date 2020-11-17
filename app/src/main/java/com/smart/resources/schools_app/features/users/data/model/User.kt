package com.smart.resources.schools_app.features.users.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = User.TABLE_NAME,
)
data class User(
    @PrimaryKey(autoGenerate = true)
    val userId: Int,
    val backendUserId: String,
) {
    companion object {
        const val TABLE_NAME = "User"
    }
}