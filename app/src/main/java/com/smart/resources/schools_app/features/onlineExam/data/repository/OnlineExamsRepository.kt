package com.smart.resources.schools_app.features.onlineExam.data.repository

import com.hadiyarajesh.flower.*
import com.orhanobut.logger.Logger
import com.smart.resources.schools_app.core.extentions.withNewData
import com.smart.resources.schools_app.core.typeConverters.room.OnlineExamStatus
import com.smart.resources.schools_app.features.onlineExam.data.local.dataSource.OnlineExamsDao
import com.smart.resources.schools_app.features.onlineExam.data.mappers.onlineExams.OnlineExamMappersFacade
import com.smart.resources.schools_app.features.onlineExam.data.remote.dataSource.OnlineExamsClient
import com.smart.resources.schools_app.features.onlineExam.data.remote.model.NetworkExamFinishedStatus
import com.smart.resources.schools_app.features.onlineExam.data.remote.model.NetworkOnlineExamStatus
import com.smart.resources.schools_app.features.onlineExam.domain.model.onlineExam.AddOnlineExam
import com.smart.resources.schools_app.features.onlineExam.domain.model.onlineExam.OnlineExam
import com.smart.resources.schools_app.features.onlineExam.domain.repository.IOnlineExamsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import java.net.HttpURLConnection

class OnlineExamsRepository(
    private val onlineExamsDao: OnlineExamsDao,
    private val onlineExamsClient: OnlineExamsClient,
    private val onlineExamMappersFacade: OnlineExamMappersFacade,
) : IOnlineExamsRepository {

    companion object {
        private const val TAG = "OnlineExamsRepository"
    }

    @ExperimentalCoroutinesApi
    override fun getUserOnlineExams(
        userId: String
    ): Flow<Resource<List<OnlineExam>>> {
        return networkBoundResource(
            fetchFromLocal = {
                onlineExamsDao
                    .getUserOnlineExams(userId)
                    .map {
                        onlineExamMappersFacade
                            .mapLocalOnlineExam(it.first().onlineExams)
                            .sortedBy { exam -> exam.examStatus.ordinal }
                    }
            },
            fetchFromRemote = {
                onlineExamsClient.getOnlineExams()
            },
            saveRemoteData = {
                try {
                    val localExams = onlineExamMappersFacade.mapNetworkToLocalOnlineExam(it)
                    onlineExamsDao.syncWithDatabase(userId, localExams)
                } catch (e: Exception) {
                    Logger.e("$TAG: $e")
                }

            },
            onFetchFailed = { errorBody, statusCode ->
                Logger.e("$TAG: $statusCode -> $errorBody")
            }
        ).catch {
            // must be used to catch exceptions
            Logger.e("$TAG: $it")
            emit(Resource.error(msg = it.message.toString()))
        }.flowOn(Dispatchers.IO)
    }

    @ExperimentalCoroutinesApi
    override fun getOnlineExam(examId: String): Flow<Resource<OnlineExam>> {
        return networkBoundResource<OnlineExam, Unit>(
            fetchFromLocal = {
                onlineExamsDao
                    .getOnlineExamById(examId)
                    .map {
                        onlineExamMappersFacade.mapLocalOnlineExam(it)
                    }
            },
            fetchFromRemote = {
                emptyFlow()
            },
            shouldFetchFromRemote = { false },
        ).distinctUntilChanged()
            .catch {
                // must be used to catch exceptions
                Logger.e("$TAG: $it")
                emit(Resource.error(msg = it.message.toString()))
            }.flowOn(Dispatchers.IO)
    }

    override suspend fun syncOnlineExam(examId: String): ApiResponse<Unit> {
        val res = onlineExamsClient.getOnlineExam(examId).first()
        if (res is ApiSuccessResponse && res.body != null) {
            val localExam = onlineExamMappersFacade.mapNetworkToLocalOnlineExam(res.body!!)
            onlineExamsDao.update(localExam)
        }

        return res.withNewData { Unit }
    }


    override suspend fun addOnlineExam(
        userId: String,
        addOnlineExam: AddOnlineExam
    ): ApiResponse<OnlineExam> {
        val networkExam = onlineExamMappersFacade.mapOnlineExamAddToNetwork(addOnlineExam)
        val res = onlineExamsClient.addOnlineExam(networkExam).first()

        if (res is ApiSuccessResponse && res.body != null) {
            val localExams = onlineExamMappersFacade.mapNetworkToLocalOnlineExam(res.body!!)
            onlineExamsDao.upsertUserOnlineExams(userId, listOf(localExams))
        }

        return res.withNewData {
            onlineExamMappersFacade.mapNetworkOnlineExam(it)
        }
    }

    override suspend fun addOnlineExamByKey(examKey: String): Resource<Unit> {
        return Resource.success(null)
    }

    override suspend fun removeOnlineExam(examId: String): ApiResponse<Unit> {
        val res = onlineExamsClient.deleteOnlineExam(examId).first()

        when (res) {
            is ApiErrorResponse -> {
                if (res.statusCode == HttpURLConnection.HTTP_NOT_FOUND) {
                    onlineExamsDao.deleteOnlineExam(examId)
                    return ApiEmptyResponse()
                }
            }
            else -> {
                onlineExamsDao.deleteOnlineExam(examId)
            }
        }
        return res
    }

    override suspend fun activateOnlineExam(examId: String): ApiResponse<Unit> {
        val res =
            onlineExamsClient.updateStatus(examId, NetworkOnlineExamStatus.createActiveStatus())
                .first()
        if (res !is ApiErrorResponse<*>) {
            onlineExamsDao.updateExamStatus(examId, OnlineExamStatus.ACTIVE.value)
        }

        return res
    }

    override suspend fun finishOnlineExam(examId: String): ApiResponse<Unit> {
        val res = onlineExamsClient.finishExam(
            examId,
            NetworkExamFinishedStatus.createExamFinishedStatus()
        ).first()
        if (res !is ApiErrorResponse<*>) {
            onlineExamsDao.updateExamStatus(examId, OnlineExamStatus.COMPLETED.value)
        }

        return res
    }
}