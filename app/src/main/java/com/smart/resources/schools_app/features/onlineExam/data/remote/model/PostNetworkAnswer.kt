package com.smart.resources.schools_app.features.onlineExam.data.remote.model

import androidx.room.Entity
import androidx.room.ForeignKey
import com.smart.resources.schools_app.features.users.data.UserAccount

class PostNetworkAnswer (
    answer: String?,
    val questionId:String,
    val studentId:String,
):NetworkAnswer(answer)