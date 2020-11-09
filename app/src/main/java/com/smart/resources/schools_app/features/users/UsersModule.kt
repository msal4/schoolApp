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
    abstract fun bindGetCurrentUserModelUseCase(getCurrentUserModelUseCase: GetCurrentUserModelUseCase): IGetCurrentUserModelUseCase
    @Binds
    abstract fun bindDeleteUserUseCase(deleteUserUseCase:DeleteUserUseCase): IDeleteUserUseCase
    @Binds
    abstract fun bindGetLocalUserIdUseCase(getLocalUserIdUseCase: GetLocalUserIdUseCase): IGetCurrentUserIdUseCase
    @Binds
    abstract fun bindGetFirstStudentLoginUseCase(getFirstStudentLoginUseCase: GetFirstStudentLoginUseCase): IGetFirstStudentLoginUseCase
    @Binds
    abstract fun bindGetFirstTeacherLoginUseCase(getFirstTeacherLoginUseCase: GetFirstTeacherLoginUseCase): IGetFirstTeacherLoginUseCase
    @Binds
    abstract fun bindSetFirstStudentLoginUseCase(setFirstStudentLoginUseCase: SetNotFirstStudentLoginUseCase): ISetNotFirstStudentLoginUseCase
    @Binds
    abstract fun bindSetFirstTeacherLoginUseCase(setFirstTeacherLoginUseCase: SetNotFirstTeacherLoginUseCase): ISetNotFirstTeacherLoginUseCase
    @Binds
    abstract fun bindIsUserLoggedUseCaseUseCase(isUserLoggedUseCase: IsUserLoggedUseCase): IIsUserLoggedUseCase

    companion object{
        @Singleton
        @Provides
        fun provideAccountDao(appDatabase: AppDatabase): AccountsDao {
            return appDatabase.getAccountDao()
        }
    }

}