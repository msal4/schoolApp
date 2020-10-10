package com.smart.resources.schools_app.features.onlineExam.data.repository

import com.hadiyarajesh.flower.ApiResponse
import com.hadiyarajesh.flower.Resource
import com.hadiyarajesh.flower.networkBoundResource
import com.smart.resources.schools_app.core.extentions.withNewData
import com.smart.resources.schools_app.features.onlineExam.data.local.dataSource.AnswersDao
import com.smart.resources.schools_app.features.onlineExam.data.mappers.answers.AnswerMappersFacade
import com.smart.resources.schools_app.features.onlineExam.data.remote.dataSource.AnswersClient
import com.smart.resources.schools_app.features.onlineExam.data.remote.model.NetworkAnswer
import com.smart.resources.schools_app.features.onlineExam.domain.model.BaseAnswer
import com.smart.resources.schools_app.features.onlineExam.domain.repository.IAnswersRepository
import com.smart.resources.schools_app.features.onlineExam.domain.repository.ListOfAnswers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class AnswersRepository(
    private val answersDao: AnswersDao,
    private val answersClient: AnswersClient,
    private val answerMappersFacade: AnswerMappersFacade,
) : IAnswersRepository {

    @ExperimentalCoroutinesApi
    override fun getStudentExamAnswers(examId: String, studentId: String): Flow<Resource<ListOfAnswers>> =
        networkBoundResource<ListOfAnswers, List<NetworkAnswer>>(
            fetchFromLocal = {
                answersDao
                    .getUserExamAnswers(examId, studentId)
                    .map {
                        answerMappersFacade.mapLocalAnswer(it)
                    }
            },
            fetchFromRemote = {
                emptyFlow()
            },
            saveRemoteData = {

            }
        )

    override suspend fun saveAnswerLocally(
        answer: BaseAnswer<Any>,
        questionId: String,
        userId: String
    ) {
        val localAnswer= answerMappersFacade.mapAnswerToLocal(answer, questionId, userId)
        answersDao.upsert(localAnswer)
    }



    override suspend fun sendAnswers(answers: ListOfAnswers, questionIds:List<String>, userId:String): ApiResponse<Unit> {
        val networkAnswers= answerMappersFacade.mapAnswerToNetwork(answers, questionIds, userId)
        val res= answersClient.addAnswers(networkAnswers).first()

        return  res.withNewData { Unit }
    }
}