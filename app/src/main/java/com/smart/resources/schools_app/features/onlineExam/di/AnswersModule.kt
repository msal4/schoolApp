package com.smart.resources.schools_app.features.onlineExam.di

import com.smart.resources.schools_app.core.appDatabase.storages.AppDatabase
import com.smart.resources.schools_app.core.network.RetrofitHelper
import com.smart.resources.schools_app.core.utils.mapList
import com.smart.resources.schools_app.features.onlineExam.data.local.dataSource.AnswersDao
import com.smart.resources.schools_app.features.onlineExam.data.mappers.answers.*
import com.smart.resources.schools_app.features.onlineExam.data.mappers.mapLocalQuestionWithAnswer
import com.smart.resources.schools_app.features.onlineExam.data.mappers.questions.QuestionMappersFacade
import com.smart.resources.schools_app.features.onlineExam.data.remote.dataSource.AnswersClient
import com.smart.resources.schools_app.features.onlineExam.data.repository.AnswersRepository
import com.smart.resources.schools_app.features.onlineExam.domain.repository.IAnswersRepository
import com.smart.resources.schools_app.features.users.data.localDataSource.UsersDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
object AnswersModule {
    @Provides
    fun provideAnswersDao(appDatabase: AppDatabase): AnswersDao {
        return appDatabase.getAnswersDao()
    }

    @Provides
    fun provideAnswersClient(): AnswersClient {
        return RetrofitHelper.answersClient
    }

    @Provides
    fun provideAnswersRepository(
        usersDao: UsersDao,
        answersDao: AnswersDao,
        answersClient: AnswersClient,
        answerMappersFacade: AnswerMappersFacade,
        questionMappersFacade: QuestionMappersFacade
    ): IAnswersRepository {
        return AnswersRepository(
            usersDao = usersDao,
            answersDao = answersDao,
            answersClient = answersClient,
            answerMappersFacade = answerMappersFacade,
            questionWithAnswerMapper = {
                mapList(
                    input = it,
                    mapSingle = { questionWithAnswer ->
                        mapLocalQuestionWithAnswer(
                            input = questionWithAnswer,
                            localQuestionMapper = questionMappersFacade::mapLocalQuestion,
                            localAnswerMapper = answerMappersFacade::mapLocalAnswer,
                        )
                    }
                )
            }
        )
    }

    @Provides
    fun provideAnswersFacade() = AnswerMappersFacade(
        localAnswerMapper = ::mapLocalAnswer,
        networkAnswerMapper = ::mapNetworkAnswer,
        answerToLocalMapper = {answer, questionId, userId ->
            mapAnswerToLocal(answer, questionId, userId)
        },
        answerToNetworkMapper = { answer, questionId, _ ->
            mapAnswerToNetwork(answer, questionId)
        },
    )
}