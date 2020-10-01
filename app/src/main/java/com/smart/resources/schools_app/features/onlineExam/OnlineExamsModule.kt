package com.smart.resources.schools_app.features.onlineExam

import com.smart.resources.schools_app.core.appDatabase.AppDatabase
import com.smart.resources.schools_app.core.utils.mapList
import com.smart.resources.schools_app.features.onlineExam.data.local.dataSource.OnlineExamsDao
import com.smart.resources.schools_app.features.onlineExam.data.local.dataSource.QuestionsDao
import com.smart.resources.schools_app.features.onlineExam.data.local.mappers.mapLocalOnlineExam
import com.smart.resources.schools_app.features.onlineExam.data.mappers.domain.mapOnlineExamToLocal
import com.smart.resources.schools_app.features.onlineExam.data.mappers.domain.mapQuestionToLocal
import com.smart.resources.schools_app.features.onlineExam.data.local.mappers.mapLocalQuestion
import com.smart.resources.schools_app.features.onlineExam.data.repository.OnlineExamsRepository
import com.smart.resources.schools_app.features.onlineExam.data.repository.QuestionsRepository
import com.smart.resources.schools_app.features.onlineExam.domain.repository.IOnlineExamsRepository
import com.smart.resources.schools_app.features.onlineExam.domain.repository.IQuestionsRepository
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
    abstract fun bindAddOnlineExamsUseCase(addOnlineExamUseCase: AddOnlineExamsUseCase): IAddOnlineExamsUseCase

    @Binds
    abstract fun bindAddOnlineExamByKeyUseCase(addOnlineExamByKeyUseCase: AddOnlineExamByKeyUseCase): IAddOnlineExamByKeyUseCase

    @Binds
    abstract fun bindRemoveOnlineExamUseCase(removeOnlineExamUseCase: RemoveOnlineExamUseCase): IRemoveOnlineExamUseCase


    companion object {
        @Provides
        fun provideOnlineExamsDao(appDatabase: AppDatabase): OnlineExamsDao {
            return appDatabase.getOnlineExamDao()
        }

        @Provides
        fun provideQuestionsDao(appDatabase: AppDatabase): QuestionsDao {
            return appDatabase.getQuestionsDao()
        }

        @Provides
        fun provideOnlineExamsRepository(onlineExamsDao: OnlineExamsDao): IOnlineExamsRepository {
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

        @Provides
        fun provideQuestionsRepository(questionsDao: QuestionsDao): IQuestionsRepository {
            return QuestionsRepository(
                questionsDao = questionsDao,
                localQuestionMapper = { questionsList ->
                    mapList(
                        input = questionsList,
                        mapSingle = ::mapLocalQuestion,
                    )
                },
                questionToLocalMapper = { questionsList, examId ->
                    mapList(
                        input = questionsList,
                        mapSingle = {
                            mapQuestionToLocal(it, examId)
                        }
                    )
                }
            )
        }
    }

}