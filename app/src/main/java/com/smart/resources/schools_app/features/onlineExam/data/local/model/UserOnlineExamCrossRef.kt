package com.smart.resources.schools_app.features.onlineExam.data.local.model

import androidx.room.Entity
import androidx.room.ForeignKey
import com.smart.resources.schools_app.features.users.data.UserAccount

@Entity(
    tableName = UserOnlineExamCrossRef.TABLE_NAME,
    primaryKeys = [
        "uid",
        "onlineExamId",
    ],
    foreignKeys = [
        ForeignKey(
            entity = UserAccount::class,
            parentColumns = ["uid"],
            childColumns = ["uid"],
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
    val uid: String,
    val onlineExamId: String,
){
    companion object{
        const val TABLE_NAME= "UserOnlineExamCrossRef"
    }
}