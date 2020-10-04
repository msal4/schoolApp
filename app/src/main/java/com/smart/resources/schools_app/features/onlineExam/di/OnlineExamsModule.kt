package com.smart.resources.schools_app.features.onlineExam.di

import com.smart.resources.schools_app.core.appDatabase.AppDatabase
import com.smart.resources.schools_app.core.utils.RetrofitHelper
import com.smart.resources.schools_app.core.utils.mapList
import com.smart.resources.schools_app.core.utils.mapNullInputList
import com.smart.resources.schools_app.features.onlineExam.data.local.dataSource.OnlineExamsDao
import com.smart.resources.schools_app.features.onlineExam.data.mappers.onlineExams.OnlineExamMappersFacade
import com.smart.resources.schools_app.features.onlineExam.data.mappers.onlineExams.mapLocalOnlineExams
import com.smart.resources.schools_app.features.onlineExam.data.mappers.onlineExams.mapNetworkOnlineExams
import com.smart.resources.schools_app.features.onlineExam.data.mappers.onlineExams.mapOnlineExam
import com.smart.resources.schools_app.features.onlineExam.data.remote.dataSource.OnlineExamsClient
import com.smart.resources.schools_app.features.onlineExam.data.repository.OnlineExamsRepository
import com.smart.resources.schools_app.features.onlineExam.domain.repository.IOnlineExamsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
object OnlineExamsModule {
    @Provides
    fun provideOnlineExamsDao(appDatabase: AppDatabase): OnlineExamsDao {
        return appDatabase.getOnlineExamDao()
    }

    @Provides
    fun provideOnlineExamsClient(): OnlineExamsClient {
        return RetrofitHelper.onlineExamsClient
    }

    @Provides
    fun provideOnlineExamsRepository(
        onlineExamsDao: OnlineExamsDao,
        onlineExamsClient: OnlineExamsClient,
        onlineExamMappersFacade: OnlineExamMappersFacade,
    ): IOnlineExamsRepository {
        return OnlineExamsRepository(
            onlineExamsClient = onlineExamsClient,
            onlineExamsDao = onlineExamsDao,
            onlineExamMappersFacade = onlineExamMappersFacade,
        )
    }

    @Provides
    fun provideOnlineExamsFacade() = OnlineExamMappersFacade(
        localOnlineExamsMapper = { localExamsList ->
            mapList(
                input = localExamsList,
                mapSingle = ::mapLocalOnlineExams,
            )
        },
        networkOnlineExamsMapper = { networkExamsList ->
            mapNullInputList(
                input = networkExamsList,
                mapSingle = ::mapNetworkOnlineExams,
            )
        },
        onlineExamsMapper = { onlineExamsList ->
            mapList(
                input = onlineExamsList,
                mapSingle = ::mapOnlineExam,
            )
        },
    )
}