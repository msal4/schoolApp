package com.smart.resources.schools_app.features.onlineExam.data.local.model

import androidx.room.Entity
import androidx.room.ForeignKey
import com.smart.resources.schools_app.features.users.data.UserAccount

@Entity(
    tableName = LocalAnswer.TABLE_NAME,
    primaryKeys = [
    "userId",
    "questionId",
    ] ,
    foreignKeys = [
        ForeignKey(
            entity = UserAccount::class,
            parentColumns = ["uid"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE,
        ),
        ForeignKey(
            entity = LocalQuestion::class,
            parentColumns = ["questionId"],
            childColumns = ["questionId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE,
        ),
    ],
)
data class LocalAnswer (
    val userId:String,
    val questionId:String,
    val answerNormal:String?=null,
    val answerMultiChoice:Int?=null,
    val answerCorrectness:Boolean?=null,
    val correctAnswer:String="",
){
    companion object{
        const val TABLE_NAME="Answers"
    }
}