package com.smart.resources.schools_app.features.onlineExam.di

import com.smart.resources.schools_app.core.appDatabase.AppDatabase
import com.smart.resources.schools_app.core.network.RetrofitHelper
import com.smart.resources.schools_app.features.onlineExam.data.local.dataSource.QuestionsDao
import com.smart.resources.schools_app.features.onlineExam.data.mappers.questions.*
import com.smart.resources.schools_app.features.onlineExam.data.remote.dataSource.QuestionsClient
import com.smart.resources.schools_app.features.onlineExam.data.repository.QuestionsRepository
import com.smart.resources.schools_app.features.onlineExam.domain.repository.IQuestionsRepository
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
    fun provideQuestionsClient(): QuestionsClient {
        return RetrofitHelper.questionsClient
    }

    @Provides
    fun provideQuestionsRepository(
        questionsDao: QuestionsDao,
        questionsClient: QuestionsClient,
        mappersFacade: QuestionMappersFacade,
    ): IQuestionsRepository {
        return QuestionsRepository(
            questionsDao = questionsDao,
            questionsClient=questionsClient,
            questionMappersFacade = mappersFacade,
        )
    }

    @Provides
    fun provideQuestionsFacade() = QuestionMappersFacade(
        localQuestionMapper = ::mapLocalQuestion,
        networkQuestionMapper = ::mapNetworkQuestion,
        questionsToLocalMapper = { question, examId ->
            mapQuestionToLocal(question,  examId)
        },
        questionsToNetworkMapper = { question, examId ->
            mapQuestionToNetwork(question,  examId)
        },
    )

}