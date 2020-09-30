package com.smart.resources.schools_app.features.onlineExam.data.repository

import com.hadiyarajesh.flower.ApiEmptyResponse
import com.hadiyarajesh.flower.Resource
import com.hadiyarajesh.flower.networkBoundResource
import com.smart.resources.schools_app.features.onlineExam.data.localDataSource.QuestionsDao
import com.smart.resources.schools_app.features.onlineExam.data.model.LocalQuestion
import com.smart.resources.schools_app.features.onlineExam.domain.model.Question
import com.smart.resources.schools_app.features.onlineExam.domain.repository.IQuestionsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class QuestionsRepository(
    private val questionsDao: QuestionsDao,
    private val localQuestionMapper: (List<LocalQuestion>) -> List<Question>,
    private val questionToLocalMapper: (List<Question>, String) -> List<LocalQuestion>
) : IQuestionsRepository {
    override fun getExamQuestions(examId: String): Flow<Resource<List<Question>>> {
        return networkBoundResource<List<Question>, Unit>(
            fetchFromLocal = {
                questionsDao.getExamQuestions(examId).map { localQuestionMapper(it) }
            },
            fetchFromRemote = {
                flow { ApiEmptyResponse<Unit>() }
            },
            shouldFetchFromRemote = { false }
        )
    }

    override suspend fun addQuestions(examId: String, vararg question: Question): Resource<Unit> {
        val localQuestion = questionToLocalMapper(question.toList(), examId)
        questionsDao.insert(*localQuestion.toTypedArray())
        return Resource.success(Unit)
    }

    override suspend fun removeExamQuestions(vararg examId: String): Resource<Unit> {
        return Resource.success(Unit)
    }

}