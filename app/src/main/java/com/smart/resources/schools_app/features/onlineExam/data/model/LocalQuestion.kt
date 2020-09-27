package com.smart.resources.schools_app.features.onlineExam.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = LocalQuestion.TABLE_NAME)
data class LocalQuestion(
    @PrimaryKey
    private val questionId:String,
    private val onlineExamId:String,
    private val questionText:String,
    private val questionType:Int,
    private val options:List<Int>?,
){
    companion object{
        const val TABLE_NAME= "Questions"
    }
}