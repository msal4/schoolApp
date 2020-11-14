package com.smart.resources.schools_app.features.users.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.smart.resources.schools_app.core.myTypes.DecryptedString

@Entity(
    tableName = Account.TABLE_NAME,
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["userId"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE,
        ),
    ],
)
data class Account(
    @PrimaryKey(autoGenerate = true)
    val userId: Int,
    val accessToken: DecryptedString,
    var img: String,
    val username: String,
    val userType: Int,
) {
    val isStudent: Boolean get() = userType == 0

    companion object {
        const val TABLE_NAME = "Account"
    }
}