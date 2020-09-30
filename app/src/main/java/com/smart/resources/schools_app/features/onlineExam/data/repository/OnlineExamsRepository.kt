package com.smart.resources.schools_app.features.onlineExam.data.repository

import com.hadiyarajesh.flower.ApiEmptyResponse
import com.hadiyarajesh.flower.Resource
import com.hadiyarajesh.flower.networkBoundResource
import com.smart.resources.schools_app.features.onlineExam.data.localDataSource.OnlineExamsDao
import com.smart.resources.schools_app.features.onlineExam.data.model.LocalOnlineExam
import com.smart.resources.schools_app.features.onlineExam.domain.model.OnlineExam
import com.smart.resources.schools_app.features.onlineExam.domain.repository.IOnlineExamsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class OnlineExamsRepository(
    private val onlineExamsDao: OnlineExamsDao,
    private val localOnlineExamsMapper: (List<LocalOnlineExam>) -> List<OnlineExam>,
    private val onlineExamsToLocalMapper: (List<OnlineExam>) -> List<LocalOnlineExam>
) : IOnlineExamsRepository {
    override fun getOnlineExams(userId: String): Flow<Resource<List<OnlineExam>>> {
        return networkBoundResource<List<OnlineExam>, Unit>(
            fetchFromLocal = {
                onlineExamsDao.getOnlineExams().map { localOnlineExamsMapper(it) }
            },
            fetchFromRemote = {
                flow { ApiEmptyResponse<Unit>() }
            },
            shouldFetchFromRemote = { false }
        )
    }

    override suspend fun addOnlineExams(vararg onlineExam: OnlineExam): Resource<Unit> {
        val localExams= onlineExamsToLocalMapper(onlineExam.toList())
        onlineExamsDao.insert(*localExams.toTypedArray())
        return Resource.success(Unit)
    }

    override suspend fun addOnlineExamByKey(examKey: String): Resource<Unit> {
        return Resource.success(null)
    }

    override suspend fun removeOnlineExam(examId: String): Resource<Unit> {
        onlineExamsDao.remove(examId)
        return Resource.success(Unit)
    }
}