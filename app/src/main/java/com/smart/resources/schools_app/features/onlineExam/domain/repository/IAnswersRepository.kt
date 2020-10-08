package com.smart.resources.schools_app.features.onlineExam.domain.repository

import com.hadiyarajesh.flower.ApiResponse
import com.hadiyarajesh.flower.Resource
import com.smart.resources.schools_app.features.onlineExam.domain.model.BaseAnswer
import com.smart.resources.schools_app.features.onlineExam.domain.model.BaseAnswerableQuestion
import com.smart.resources.schools_app.features.onlineExam.domain.model.Question
import kotlinx.coroutines.flow.Flow

typealias ListOfAnswers = List<BaseAnswer<out Any>>

interface IAnswersRepository {
    fun getAnswers(examId: String, studentId:String): Flow<Resource<ListOfAnswers>>
    fun saveAnswersLocally(answers:ListOfAnswers)
    fun sendAnswers(answers:ListOfAnswers):ApiResponse<Unit>
}