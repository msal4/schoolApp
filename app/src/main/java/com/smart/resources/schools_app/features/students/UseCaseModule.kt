package com.smart.resources.schools_app.features.students

import com.smart.resources.schools_app.features.students.usecases.GetClassStudentsUseCase
import com.smart.resources.schools_app.features.students.usecases.GetStudentsWithAnswerStatusUseCase
import com.smart.resources.schools_app.features.students.usecases.IGetClassStudentsUseCase
import com.smart.resources.schools_app.features.students.usecases.IGetStudentsWithAnswerStatus
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
interface UseCaseModule {
    @Binds
    fun bindGetClassStudentsUseCase(getClassStudentsUseCase: GetClassStudentsUseCase): IGetClassStudentsUseCase

    @Binds
    fun bindGetStudentsWithAnswersUseCase(getStudentsWithAnswerStatus: GetStudentsWithAnswerStatusUseCase): IGetStudentsWithAnswerStatus
}