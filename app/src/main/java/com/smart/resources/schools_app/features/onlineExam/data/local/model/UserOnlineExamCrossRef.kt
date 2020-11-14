package com.smart.resources.schools_app.features.onlineExam.data.local.model

import androidx.room.Entity
import androidx.room.ForeignKey
import com.smart.resources.schools_app.features.users.data.model.User

@Entity(
    tableName = UserOnlineExamCrossRef.TABLE_NAME,
    primaryKeys = [
        "userId",
        "onlineExamId",
    ],
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["userId"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE,
        ),
        ForeignKey(
            entity = LocalOnlineExam::class,
            parentColumns = ["onlineExamId"],
            childColumns = ["onlineExamId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE,
        ),
    ],
)
data class UserOnlineExamCrossRef(
    val userId: String,
    val onlineExamId: String,
){
    companion object{
        const val TABLE_NAME= "UserOnlineExamCrossRef"
    }
}