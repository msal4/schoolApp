package com.smart.resources.schools_app.features.onlineExam.data.remote.model

class NetworkExamFinishedStatus private constructor(
    private val finished:Int
){
     companion object{
         fun createExamNotFinishedStatus() = NetworkExamFinishedStatus(0)
         fun createExamFinishedStatus() = NetworkExamFinishedStatus(1)
     }
}