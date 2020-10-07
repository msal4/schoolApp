package com.smart.resources.schools_app.features.students

import com.smart.resources.schools_app.features.onlineExam.domain.usecase.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
interface UseCaseModule {
    @Binds
    fun bindGetClassStudentsUseCase(getClassStudentsUseCase: GetClassStudentsUseCase): IGetClassStudentsUseCase

}