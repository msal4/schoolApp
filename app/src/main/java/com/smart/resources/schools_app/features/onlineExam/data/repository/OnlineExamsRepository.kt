package com.smart.resources.schools_app.features.onlineExam.data.repository

import com.hadiyarajesh.flower.ApiResponse
import com.hadiyarajesh.flower.ApiSuccessResponse
import com.hadiyarajesh.flower.Resource
import com.hadiyarajesh.flower.networkBoundResource
import com.orhanobut.logger.Logger
import com.smart.resources.schools_app.core.extentions.withNewData
import com.smart.resources.schools_app.features.onlineExam.data.local.dataSource.OnlineExamsDao
import com.smart.resources.schools_app.features.onlineExam.data.mappers.onlineExams.OnlineExamMappersFacade
import com.smart.resources.schools_app.features.onlineExam.data.remote.dataSource.OnlineExamsClient
import com.smart.resources.schools_app.features.onlineExam.domain.model.onlineExam.OnlineExam
import com.smart.resources.schools_app.features.onlineExam.domain.model.onlineExam.AddOnlineExam
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
                        onlineExamMappersFacade.mapLocalOnlineExam(it.first().onlineExams)
                    }
            },
            fetchFromRemote = {
                onlineExamsClient.getOnlineExams()
            },
            saveRemoteData = {
                    val localExams= onlineExamMappersFacade.mapNetworkToLocalOnlineExam(it)
                    onlineExamsDao.upsertUserOnlineExams(userId,localExams)
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

    override suspend fun addOnlineExam(userId: String, addOnlineExam: AddOnlineExam): ApiResponse<OnlineExam> {
        val networkExam = onlineExamMappersFacade.mapOnlineExamAddToNetwork(addOnlineExam)
        val res= onlineExamsClient.addOnlineExam(networkExam)

        if(res is ApiSuccessResponse && res.body!=null){
            val localExams= onlineExamMappersFacade.mapNetworkToLocalOnlineExam(res.body!!)
             onlineExamsDao.upsertUserOnlineExams(userId, listOf(localExams))
        }

        return res.withNewData {
            onlineExamMappersFacade.mapNetworkOnlineExam(it)
        }
    }

    override suspend fun addOnlineExamByKey(examKey: String): Resource<Unit> {
        return Resource.success(null)
    }

    override suspend fun removeOnlineExam(examId: String): Resource<Unit> {
        onlineExamsDao.removeUserOnlineExam(examId)
        return Resource.success(Unit)
    }
}