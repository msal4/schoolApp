package com.smart.resources.schools_app.features.onlineExam.data.remote.model

class NetworkOnlineExamStatus private constructor(
    private val status:Int,
){
    companion object{
        fun createInActiveStatus() = NetworkOnlineExamStatus(0)
        fun createActiveStatus() = NetworkOnlineExamStatus(1)
    }
}