package com.smart.resources.schools_app.features.onlineExam

import com.smart.resources.schools_app.core.appDatabase.AppDatabase
import com.smart.resources.schools_app.core.utils.mapList
import com.smart.resources.schools_app.features.onlineExam.data.localDataSource.OnlineExamsDao
import com.smart.resources.schools_app.features.onlineExam.data.mappers.mapLocalOnlineExam
import com.smart.resources.schools_app.features.onlineExam.data.mappers.mapOnlineExamToLocal
import com.smart.resources.schools_app.features.onlineExam.data.repository.OnlineExamsRepository
import com.smart.resources.schools_app.features.onlineExam.domain.repository.IOnlineExamsRepository
import com.smart.resources.schools_app.features.onlineExam.domain.usecase.*
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class OnlineExamsModule {


    @Binds
    abstract fun bindGetOnlineExamsUseCase(getOnlineExamsUseCase: GetOnlineExamsUseCase): IGetOnlineExamsUseCase

    @Binds
    abstract fun bindAddOnlineExamUseCase(addOnlineExamUseCase: AddOnlineExamsUseCase): IAddOnlineExamsUseCase

    @Binds
    abstract fun bindRemoveOnlineExamUseCase(removeOnlineExamUseCase: RemoveOnlineExamUseCase): IRemoveOnlineExamUseCase


    companion object {
        @Provides
        fun provideOnlineExamsDao(appDatabase: AppDatabase): OnlineExamsDao {
            return appDatabase.getOnlineExamDao()
        }

        @Provides
        fun bindOnlineExamsRepository(onlineExamsDao: OnlineExamsDao): IOnlineExamsRepository {
            return OnlineExamsRepository(
                onlineExamsDao = onlineExamsDao,
                localOnlineExamsMapper = { localList ->
                    mapList(
                        input = localList,
                        mapSingle = ::mapLocalOnlineExam,
                    )
                },
                onlineExamsToLocalMapper = {
                    mapList(
                        input = it,
                        mapSingle = ::mapOnlineExamToLocal
                    )
                }
            )
        }
    }

}