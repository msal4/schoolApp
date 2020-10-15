package com.smart.resources.schools_app.features.users

import com.smart.resources.schools_app.core.appDatabase.storages.AppDatabase
import com.smart.resources.schools_app.features.users.data.AccountsDao
import com.smart.resources.schools_app.features.users.domain.usecase.*
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
abstract class UsersModule {
    @Binds
    abstract fun bindGetCurrentUserTypeUseCase(getCurrentUserTypeUseCase: GetCurrentUserTypeUseCase): IGetCurrentUserTypeUseCase
    @Binds
    abstract fun bindGetCurrentTeacherDataUseCase(getCurrentTeacherModelUseCase: GetCurrentTeacherModelUseCase): IGetCurrentTeacherModelUseCase
    @Binds
    abstract fun bindGetLocalUserIdUseCase(getLocalUserIdUseCase: GetLocalUserIdUseCase): IGetUserIdUseCase

    companion object{
        @Singleton
        @Provides
        fun provideAccountDao(appDatabase: AppDatabase): AccountsDao {
            return appDatabase.getAccountDao()
        }
    }

}