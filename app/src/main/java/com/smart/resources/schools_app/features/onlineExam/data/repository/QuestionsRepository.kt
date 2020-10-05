package com.smart.resources.schools_app.features.onlineExam.data.repository

import com.hadiyarajesh.flower.*
import com.smart.resources.schools_app.core.extentions.withNewData
import com.smart.resources.schools_app.features.onlineExam.data.local.dataSource.QuestionsDao
import com.smart.resources.schools_app.features.onlineExam.data.mappers.questions.QuestionMappersFacade
import com.smart.resources.schools_app.features.onlineExam.data.remote.dataSource.QuestionsClient
import com.smart.resources.schools_app.features.onlineExam.domain.model.Question
import com.smart.resources.schools_app.features.onlineExam.domain.repository.IQuestionsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class QuestionsRepository(
    private val questionsDao: QuestionsDao,
    private val questionsClient: QuestionsClient,
    private val questionMappersFacade: QuestionMappersFacade,
) : IQuestionsRepository {

    @ExperimentalCoroutinesApi
    override fun getExamQuestions(examId: String): Flow<Resource<List<Question>>> {
        return networkBoundResource<List<Question>, Unit>(
            fetchFromLocal = {
                questionsDao.getExamQuestions(examId)
                    .map { questionMappersFacade.mapLocalQuestion(it) }
            },
            fetchFromRemote = {
                flow { ApiEmptyResponse<Unit>() }
            },
            shouldFetchFromRemote = { false }
        )
    }

    override suspend fun addQuestions(
        examId: String,
        questions: List<Question>
    ): ApiResponse<List<Question>> {
        val res =
            questionsClient.addQuestions(questionMappersFacade.mapQuestionToNetwork(questions, examId))

        if (res is ApiSuccessResponse && res.body != null) {
            val localQuestions =
                questionMappersFacade.mapNetworkToLocalQuestion(res.body.orEmpty())
            questionsDao.upsert(localQuestions)
        }

        return res.withNewData {
            questionMappersFacade.mapNetworkQuestion(it)
        }
    }

    override suspend fun removeExamQuestions(vararg examId: String): Resource<Unit> {
        return Resource.success(Unit)
    }

}