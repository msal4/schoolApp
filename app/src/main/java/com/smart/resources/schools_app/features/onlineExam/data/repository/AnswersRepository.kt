package com.smart.resources.schools_app.features.onlineExam.data.repository

import com.hadiyarajesh.flower.ApiResponse
import com.hadiyarajesh.flower.Resource
import com.hadiyarajesh.flower.networkBoundResource
import com.orhanobut.logger.Logger
import com.smart.resources.schools_app.core.extentions.TAG
import com.smart.resources.schools_app.core.extentions.withNewData
import com.smart.resources.schools_app.features.onlineExam.data.local.dataSource.AnswersDao
import com.smart.resources.schools_app.features.onlineExam.data.local.model.QuestionWithAnswer
import com.smart.resources.schools_app.features.onlineExam.data.mappers.answers.AnswerMappersFacade
import com.smart.resources.schools_app.features.onlineExam.data.remote.dataSource.AnswersClient
import com.smart.resources.schools_app.features.onlineExam.data.remote.model.NetworkAnswer
import com.smart.resources.schools_app.features.onlineExam.domain.model.BaseAnswer
import com.smart.resources.schools_app.features.onlineExam.domain.model.BaseAnswerableQuestion
import com.smart.resources.schools_app.features.onlineExam.domain.repository.IAnswersRepository
import com.smart.resources.schools_app.features.onlineExam.domain.repository.ListOfAnswers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class AnswersRepository(
    private val answersDao: AnswersDao,
    private val answersClient: AnswersClient,
    private val answerMappersFacade: AnswerMappersFacade,
    private val questionWithAnswerMapper: (List<QuestionWithAnswer>)-> List<BaseAnswerableQuestion>
) : IAnswersRepository {

    @ExperimentalCoroutinesApi
    override fun getStudentExamAnswers(examId: String, studentId: String, shouldFetchFromRemote:Boolean): Flow<Resource<List<BaseAnswerableQuestion>>> =
        networkBoundResource<List<BaseAnswerableQuestion>, List<NetworkAnswer>>(
            fetchFromLocal = {
                answersDao
                    .getUserExamQuestionsWithAnswers(examId, studentId)
                    .map {
                        Logger.wtf(it.toString())
                        questionWithAnswerMapper(it)
                    }
            },
            shouldFetchFromRemote = {
               shouldFetchFromRemote|| shouldFetchAnswers(it)
            },
            fetchFromRemote = {
                answersClient.getStudentExamAnswers(examId, studentId.substring(1, studentId.length))
            },
            saveRemoteData = {
                CoroutineScope(IO).launch {
                    val localAnswers= answerMappersFacade.mapNetworkToLocalAnswer(it, studentId)
                    answersDao.insert(localAnswers)
                }
            }
        ).catch {
            // must be used to catch exceptions
            Logger.e("$TAG: $it")
            emit(Resource.error(msg = it.message.toString()))
        }.flowOn(Dispatchers.IO)

    private fun shouldFetchAnswers(it: List<BaseAnswerableQuestion>?):Boolean =
        it?.mapNotNull { e -> e.answer }.isNullOrEmpty()

    override suspend fun saveAnswerLocally(
        answer: BaseAnswer,
        questionId: String,
        userId: String
    ) {
        try{
            val localAnswer= answerMappersFacade.mapAnswerToLocal(answer, questionId, userId)
            answersDao.upsert(localAnswer)
        }catch (e:Exception){
            Logger.e("$TAG: $e")
        }
    }

    override suspend fun sendAnswers(answers: ListOfAnswers, questionIds:List<String>, userId:String): ApiResponse<Unit> {
        val networkId= userId.substring(1, userId.length)
        val networkAnswers= answerMappersFacade.mapAnswerToNetwork(answers, questionIds, networkId)
        val res= answersClient.addAnswers(networkAnswers).first()

        return  res.withNewData { Unit }
    }
}