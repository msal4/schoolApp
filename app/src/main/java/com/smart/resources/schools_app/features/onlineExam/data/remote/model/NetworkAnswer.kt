package com.smart.resources.schools_app.features.onlineExam.data.remote.model

import androidx.room.Entity
import androidx.room.ForeignKey
import com.smart.resources.schools_app.features.users.data.UserAccount


data class NetworkAnswer (
    val answer:String?
){
    companion object{
        const val TABLE_NAME="Answers"
    }
}