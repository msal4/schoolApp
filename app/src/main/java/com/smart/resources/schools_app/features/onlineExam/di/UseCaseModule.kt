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
    fun bindGetUserOnlineExamsUseCase(getUserOnlineExamsUseCase: GetUserOnlineExamsUseCase): IGetUserOnlineExamsUseCase

    @Binds
    fun bindGetOnlineExamUseCase(getOnlineExamUseCase: GetOnlineExamUseCase): IGetOnlineExamUseCase

    @Binds
    fun bindGetOnlineExamQuestionsUseCase(getOnlineExamQuestionsUseCase: GetOnlineExamQuestionsUseCase): IGetOnlineExamQuestionsUseCase

    @Binds
    fun bindAddOnlineExamsUseCase(addOnlineExamUseCase: AddOnlineExamUseCase): IAddOnlineExamUseCase

    @Binds
    fun bindAddOnlineExamByKeyUseCase(addOnlineExamByKeyUseCase: AddOnlineExamByKeyUseCase): IAddOnlineExamByKeyUseCase

    @Binds
    fun bindRemoveOnlineExamUseCase(removeOnlineExamUseCase: RemoveOnlineExamUseCase): IRemoveOnlineExamUseCase

    @Binds
    fun bindFinishOnlineExamUseCase(finishExamUseCase: FinishOnlineExamUseCase): IFinishOnlineExamUseCase

    @Binds
    fun bindActivateOnlineExamUseCase(activateExamUseCase: ActivateOnlineExamUseCase): IActivateOnlineExamUseCase
}