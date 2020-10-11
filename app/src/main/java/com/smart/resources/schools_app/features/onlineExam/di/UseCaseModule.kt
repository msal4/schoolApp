package com.smart.resources.schools_app.features.onlineExam.di

import com.smart.resources.schools_app.features.onlineExam.domain.usecase.*
import com.smart.resources.schools_app.features.onlineExam.domain.usecase.answers.GetStudentExamAnswersUseCase
import com.smart.resources.schools_app.features.onlineExam.domain.usecase.answers.SaveAnswerLocallyUseCase
import com.smart.resources.schools_app.features.onlineExam.domain.usecase.answers.SendAnswersUseCase
import com.smart.resources.schools_app.features.onlineExam.domain.usecase.questions.GetExamQuestionsUseCase
import com.smart.resources.schools_app.features.onlineExam.domain.usecase.questions.GetExamQuestionsWithAnswersUseCase
import com.smart.resources.schools_app.features.onlineExam.domain.usecase.questions.SyncOnlineExamUseCase
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
    fun bindGetOnlineExamUseCase(getUserOnlineExamUseCase: GetUserOnlineExamUseCase): IGetOnlineExamUseCase

    @Binds
    fun bindSyncOnlineExamUseCase(syncOnlineExamUseCase: SyncOnlineExamUseCase): ISyncOnlineExamUseCase

    @Binds
    fun bindGetOnlineExamQuestionsUseCase(getOnlineExamQuestionsUseCase: GetExamQuestionsUseCase): IGetExamQuestionsUseCase

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

    @Binds
    fun bindSendAnswersUseCase(sendAnswersUseCase: SendAnswersUseCase): ISendAnswersUseCase

    @Binds
    fun bindSaveAnswerLocallyUseCase(saveAnswerLocallyUseCase: SaveAnswerLocallyUseCase): ISaveAnswerLocallyUseCase

    @Binds
    fun bindGetStudentExamAnswersUseCase(getStudentExamAnswersUseCase: GetStudentExamAnswersUseCase): IGetStudentExamAnswersUseCase

    @Binds
    fun bindGetExamQuestionsWithAnswersUseCase(getExamQuestionsWithAnswersUseCase: GetExamQuestionsWithAnswersUseCase): IGetExamQuestionsWithAnswersUseCase
}