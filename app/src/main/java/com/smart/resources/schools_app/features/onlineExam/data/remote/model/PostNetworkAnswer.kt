package com.smart.resources.schools_app.features.onlineExam.data.remote.model

class PostNetworkAnswer (
    questionId:String,
    answer: String?=null,
    chosen: Int?=null,
    TF: Boolean?=null,
):NetworkAnswer(questionId, answer, chosen, TF)