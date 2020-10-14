package com.smart.resources.schools_app.features.onlineExam.data.repository

import com.hadiyarajesh.flower.*
import com.orhanobut.logger.Logger
import com.smart.resources.schools_app.core.extentions.TAG
import com.smart.resources.schools_app.core.extentions.withNewData
import com.smart.resources.schools_app.features.onlineExam.data.local.dataSource.AnswersDao
import com.smart.resources.schools_app.features.onlineExam.data.mappers.answers.AnswerMappersFacade
import com.smart.resources.schools_app.features.onlineExam.data.remote.dataSource.AnswersClient
import com.smart.resources.schools_app.features.onlineExam.data.remote.model.NetworkAnswer
import com.smart.resources.schools_app.features.onlineExam.domain.model.BaseAnswer
import com.smart.resources.schools_app.features.onlineExam.domain.repository.IAnswersRepository
import com.smart.resources.schools_app.features.onlineExam.domain.repository.ListOfAnswers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

class AnswersRepository(
    private val answersDao: AnswersDao,
    private val answersClient: AnswersClient,
    private val answerMappersFacade: AnswerMappersFacade,
) : IAnswersRepository {

    @ExperimentalCoroutinesApi
    override fun getStudentExamAnswers(examId: String, studentId: String, shouldFetchFromRemote:Boolean): Flow<Resource<ListOfAnswers>> =
        networkBoundResource<ListOfAnswers, List<NetworkAnswer>>(
            fetchFromLocal = {
                answersDao
                    .getUserExamAnswers(examId, studentId)
                    .map {
                        answerMappersFacade.mapLocalAnswer(it)
                    }
            },
            shouldFetchFromRemote = {
                // TODO: complete
               // shouldFetchFromRemote || it.isNullOrEmpty()
                false
            },
            fetchFromRemote = {
                // TODO: fetch from api if local db is empty only
                flow {}
            },
            saveRemoteData = {

            }
        ).catch {
            // must be used to catch exceptions
            Logger.e("$TAG: $it")
            emit(Resource.error(msg = it.message.toString()))
        }.flowOn(Dispatchers.IO)

    override suspend fun saveAnswerLocally(
        answer: BaseAnswer<Any>,
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
        val networkAnswers= answerMappersFacade.mapAnswerToNetwork(answers, questionIds, userId)
        val res= answersClient.addAnswers(networkAnswers).first()

        return  res.withNewData { Unit }
    }
}