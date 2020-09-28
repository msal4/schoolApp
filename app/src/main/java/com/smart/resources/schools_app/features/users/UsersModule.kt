package com.smart.resources.schools_app.features.users

import com.smart.resources.schools_app.core.appDatabase.AppDatabase
import com.smart.resources.schools_app.features.users.data.AccountsDao
import com.smart.resources.schools_app.features.users.domain.usecase.GetCurrentUserTypeUseCase
import com.smart.resources.schools_app.features.users.domain.usecase.IGetCurrentUserTypeUseCase
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
    abstract fun provideGetCurrentUserType(getCurrentUserTypeUseCase: GetCurrentUserTypeUseCase): IGetCurrentUserTypeUseCase

    companion object{
        @Singleton
        @Provides
        fun provideAccountDao(appDatabase: AppDatabase): AccountsDao {
            return appDatabase.getAccountDao()
        }
    }

}