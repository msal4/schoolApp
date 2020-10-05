package com.smart.resources.schools_app.features.onlineExam.di

import com.smart.resources.schools_app.features.onlineExam.domain.usecase.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
interface UseCaseModule {
    @Binds
    fun bindGetOnlineExamsUseCase(getOnlineExamsUseCase: GetOnlineExamsUseCase): IGetOnlineExamsUseCase

    @Binds
    fun bindAddOnlineExamsUseCase(addOnlineExamUseCase: AddOnlineExamUseCase): IAddOnlineExamUseCase

    @Binds
    fun bindAddOnlineExamByKeyUseCase(addOnlineExamByKeyUseCase: AddOnlineExamByKeyUseCase): IAddOnlineExamByKeyUseCase

    @Binds
    fun bindRemoveOnlineExamUseCase(removeOnlineExamUseCase: RemoveOnlineExamUseCase): IRemoveOnlineExamUseCase

}