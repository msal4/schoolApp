package com.smart.resources.schools_app.features.onlineExam.di

import com.smart.resources.schools_app.core.appDatabase.AppDatabase
import com.smart.resources.schools_app.core.utils.mapList
import com.smart.resources.schools_app.features.onlineExam.data.local.dataSource.OnlineExamsDao
import com.smart.resources.schools_app.features.onlineExam.data.local.dataSource.QuestionsDao
import com.smart.resources.schools_app.features.onlineExam.data.mappers.onlineExams.mapLocalOnlineExams
import com.smart.resources.schools_app.features.onlineExam.data.mappers.onlineExams.mapOnlineExam
import com.smart.resources.schools_app.features.onlineExam.data.mappers.questions.mapQuestionToLocal
import com.smart.resources.schools_app.features.onlineExam.data.mappers.questions.mapLocalQuestion
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
object QuestionsModule {
        @Provides
        fun provideQuestionsDao(appDatabase: AppDatabase): QuestionsDao {
            return appDatabase.getQuestionsDao()
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