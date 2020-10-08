package com.smart.resources.schools_app.features.onlineExam.data.repository

import com.hadiyarajesh.flower.ApiResponse
import com.hadiyarajesh.flower.ApiSuccessResponse
import com.hadiyarajesh.flower.Resource
import com.hadiyarajesh.flower.networkBoundResource
import com.smart.resources.schools_app.core.extentions.withNewData
import com.smart.resources.schools_app.features.onlineExam.data.local.dataSource.AnswersDao
import com.smart.resources.schools_app.features.onlineExam.data.local.dataSource.QuestionsDao
import com.smart.resources.schools_app.features.onlineExam.data.mappers.questions.QuestionMappersFacade
import com.smart.resources.schools_app.features.onlineExam.data.remote.dataSource.QuestionsClient
import com.smart.resources.schools_app.features.onlineExam.domain.model.Question
import com.smart.resources.schools_app.features.onlineExam.domain.repository.IAnswersRepository
import com.smart.resources.schools_app.features.onlineExam.domain.repository.IQuestionsRepository
import com.smart.resources.schools_app.features.onlineExam.domain.repository.ListOfAnswers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class AnswersRepository(
    private val answersDao: AnswersDao,
) : IAnswersRepository {
    override fun getAnswers(examId: String, studentId: String): Flow<Resource<ListOfAnswers>> {
        TODO("Not yet implemented")
    }

    override fun saveAnswersLocally(answers: ListOfAnswers) {
    }

    override fun sendAnswers(answers: ListOfAnswers): ApiResponse<Unit> {
        TODO("Not yet implemented")
    }
}