package com.smart.resources.schools_app.features.onlineExam.data.repository

import com.hadiyarajesh.flower.Resource
import com.hadiyarajesh.flower.networkBoundResource
import com.orhanobut.logger.Logger
import com.smart.resources.schools_app.features.onlineExam.data.local.dataSource.OnlineExamsDao
import com.smart.resources.schools_app.features.onlineExam.data.mappers.onlineExams.OnlineExamMappersFacade
import com.smart.resources.schools_app.features.onlineExam.data.remote.dataSource.OnlineExamsClient
import com.smart.resources.schools_app.features.onlineExam.domain.model.OnlineExam
import com.smart.resources.schools_app.features.onlineExam.domain.repository.IOnlineExamsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class OnlineExamsRepository(
    private val onlineExamsDao: OnlineExamsDao,
    private val onlineExamsClient: OnlineExamsClient,
    private val onlineExamMappersFacade: OnlineExamMappersFacade,
) : IOnlineExamsRepository {

    companion object{
        private const val TAG= "OnlineExamsRepository"
    }

    @ExperimentalCoroutinesApi
    override fun getOnlineExams(
        userId: String
    ): Flow<Resource<List<OnlineExam>>> {
        return networkBoundResource(
            fetchFromLocal = {
                onlineExamsDao
                    .getUserOnlineExams(userId)
                    .map {
                        onlineExamMappersFacade.mapLocalOnlineExams(it.first().onlineExams)
                    }
            },
            fetchFromRemote = {
                onlineExamsClient.getOnlineExams()
            },
            saveRemoteData = {
                    val localExams= onlineExamMappersFacade.mapNetworkToLocalOnlineExams(it)
                    onlineExamsDao.insertUserOnlineExams(userId,localExams)
            },
            onFetchFailed = {errorBody, statusCode ->
                Logger.e("$TAG: $statusCode -> $errorBody")
            }
        ).catch {
            // must be used to catch exceptions
            Logger.e("$TAG: $it")
            emit(Resource.error(msg = it.message.toString()))
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun addOnlineExams(userId: String, vararg onlineExam: OnlineExam): Resource<Unit> {
        val localExams = onlineExamMappersFacade.mapOnlineExams(onlineExam.toList())
        onlineExamsDao.insertUserOnlineExams(userId, localExams)
        return Resource.success(Unit)
    }

    override suspend fun addOnlineExamByKey(examKey: String): Resource<Unit> {
        return Resource.success(null)
    }

    override suspend fun removeOnlineExam(examId: String): Resource<Unit> {
        onlineExamsDao.removeUserOnlineExam(examId)
        return Resource.success(Unit)
    }
}