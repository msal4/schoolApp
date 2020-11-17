package com.smart.resources.schools_app.features.onlineExam.data.repository

import com.hadiyarajesh.flower.ApiResponse
import com.hadiyarajesh.flower.Resource
import com.hadiyarajesh.flower.networkBoundResource
import com.orhanobut.logger.Logger
import com.smart.resources.schools_app.core.extentions.withNewData
import com.smart.resources.schools_app.features.onlineExam.data.local.dataSource.AnswersDao
import com.smart.resources.schools_app.features.onlineExam.data.local.model.QuestionWithAnswer
import com.smart.resources.schools_app.features.onlineExam.data.mappers.answers.AnswerMappersFacade
import com.smart.resources.schools_app.features.onlineExam.data.remote.dataSource.AnswersClient
import com.smart.resources.schools_app.features.onlineExam.domain.model.BaseAnswer
import com.smart.resources.schools_app.features.onlineExam.domain.model.BaseAnswerableQuestion
import com.smart.resources.schools_app.features.onlineExam.domain.repository.IAnswersRepository
import com.smart.resources.schools_app.features.onlineExam.domain.repository.ListOfAnswers
import com.smart.resources.schools_app.features.users.data.localDataSource.UsersDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class AnswersRepository(
    private val answersDao: AnswersDao,
    private val usersDao: UsersDao,
    private val answersClient: AnswersClient,
    private val answerMappersFacade: AnswerMappersFacade,
    private val questionWithAnswerMapper: (List<QuestionWithAnswer>) -> List<BaseAnswerableQuestion>
) : IAnswersRepository {
    companion object{
        private const val TAG= "AnswersRepository"
    }

    @ExperimentalCoroutinesApi
    override fun getStudentExamAnswers(
        examId: String,
        backendUserId: String,
        shouldFetchFromRemote: Boolean,
    ): Flow<Resource<List<BaseAnswerableQuestion>>> =
        networkBoundResource(
            fetchFromLocal = {
                answersDao
                    .getUserExamQuestionsWithAnswers(examId, backendUserId)
                    .map {
                        questionWithAnswerMapper(it)
                    }
            },
            shouldFetchFromRemote = {
                shouldFetchFromRemote || shouldFetchAnswers(it)
            },
            fetchFromRemote = {
                answersClient.getStudentExamAnswers(examId, backendUserId)
            },
            saveRemoteData = {
                CoroutineScope(IO).launch {
                    val userId= usersDao.getStudentByBackendUserId(backendUserId).userId.toString()
                    Logger.wtf("$TAG: userId,$userId")
                    val localAnswers = answerMappersFacade.mapNetworkToLocalAnswer(it, userId)
                    answersDao.insert(localAnswers)
                }
            }
        ).catch {
            // must be used to catch exceptions
            Logger.e("$TAG: $it")
            emit(Resource.error(msg = it.message.toString()))
        }.flowOn(IO)

    private fun shouldFetchAnswers(it: List<BaseAnswerableQuestion>?): Boolean =
        it?.mapNotNull { e -> e.answer }.isNullOrEmpty()

    override suspend fun saveAnswerLocally(
        answer: BaseAnswer,
        questionId: String,
        userId: String
    ) {
        try {
            val localAnswer = answerMappersFacade.mapAnswerToLocal(answer, questionId, userId)
            answersDao.upsert(localAnswer)
        } catch (e: Exception) {
            Logger.e("$TAG: $e")
        }
    }

    override suspend fun sendAnswers(
        answers: ListOfAnswers,
        questionIds: List<String>,
        backendUserId: String
    ): ApiResponse<Unit> {
        val networkAnswers =
            answerMappersFacade.mapAnswerToNetwork(answers, questionIds, backendUserId)
        val res = answersClient.addAnswers(networkAnswers).first()

        return res.withNewData { Unit }
    }
}