package com.smart.resources.schools_app.features.onlineExam.domain.repository

import com.hadiyarajesh.flower.ApiResponse
import com.hadiyarajesh.flower.Resource
import com.smart.resources.schools_app.features.onlineExam.domain.model.Question
import kotlinx.coroutines.flow.Flow

interface IQuestionsRepository {
    fun getExamQuestions(examId: String): Flow<Resource<List<Question>>>
    suspend fun addQuestions(examId: String, questions: List<Question>): ApiResponse<List<Question>>
    suspend fun removeExamQuestions(vararg examId: String): Resource<Unit>
}