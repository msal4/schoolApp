package com.smart.resources.schools_app.features.onlineExam.domain.repository

import com.hadiyarajesh.flower.ApiResponse
import com.hadiyarajesh.flower.Resource
import com.smart.resources.schools_app.features.onlineExam.domain.model.BaseAnswer
import com.smart.resources.schools_app.features.onlineExam.domain.model.BaseAnswerableQuestion
import com.smart.resources.schools_app.features.onlineExam.domain.model.Question
import kotlinx.coroutines.flow.Flow

typealias ListOfAnswers = List<BaseAnswer>

interface IAnswersRepository {
    fun getStudentExamAnswers(examId: String, studentId:String, shouldFetchFromRemote:Boolean): Flow<Resource<ListOfAnswers>>
    suspend fun saveAnswerLocally(answer:BaseAnswer, questionId:String, userId:String)
    suspend fun sendAnswers(answers:ListOfAnswers, questionIds:List<String>, userId:String):ApiResponse<Unit>
}