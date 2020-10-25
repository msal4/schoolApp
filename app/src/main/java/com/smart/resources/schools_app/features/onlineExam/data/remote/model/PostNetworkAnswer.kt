package com.smart.resources.schools_app.features.onlineExam.data.remote.model

import androidx.room.Entity
import androidx.room.ForeignKey
import com.smart.resources.schools_app.features.users.data.UserAccount

class PostNetworkAnswer (
    questionId:String,
    answer: String?=null,
    chosen: Int?=null,
    TF: Boolean?=null,
):NetworkAnswer(questionId, answer, chosen, TF)