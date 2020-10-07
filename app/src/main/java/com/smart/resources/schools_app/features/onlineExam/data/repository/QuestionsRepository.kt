package com.smart.resources.schools_app.features.onlineExam.data.repository

import com.hadiyarajesh.flower.ApiResponse
import com.hadiyarajesh.flower.ApiSuccessResponse
import com.hadiyarajesh.flower.Resource
import com.hadiyarajesh.flower.networkBoundResource
import com.smart.resources.schools_app.core.extentions.withNewData
import com.smart.resources.schools_app.features.onlineExam.data.local.dataSource.QuestionsDao
import com.smart.resources.schools_app.features.onlineExam.data.mappers.questions.QuestionMappersFacade
import com.smart.resources.schools_app.features.onlineExam.data.remote.dataSource.QuestionsClient
import com.smart.resources.schools_app.features.onlineExam.domain.model.Question
import com.smart.resources.schools_app.features.onlineExam.domain.repository.IQuestionsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class QuestionsRepository(
    private val questionsDao: QuestionsDao,
    private val questionsClient: QuestionsClient,
    private val questionMappersFacade: QuestionMappersFacade,
) : IQuestionsRepository {

    @ExperimentalCoroutinesApi
    override fun getExamQuestions(examId: String): Flow<Resource<List<Question>>> {
        return networkBoundResource(
            fetchFromLocal = {
                questionsDao.getExamQuestions(examId)
                    .map { questionMappersFacade.mapLocalQuestion(it) }
            },
            fetchFromRemote = {
                questionsClient.getExamQuestions(examId)
            },
            saveRemoteData = {
                questionsDao.syncWithDatabase(
                    examId,
                    questionMappersFacade.mapNetworkToLocalQuestion(it),
                )
            }
        )
    }

    override suspend fun addQuestions(
        examId: String,
        questions: List<Question>
    ): ApiResponse<List<Question>> {
        val res =
            questionsClient.addQuestions(
                questionMappersFacade.mapQuestionToNetwork(
                    questions,
                    examId,
                )
            ).first()

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