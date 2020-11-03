package com.smart.resources.schools_app.features.users.domain.usecase

import com.smart.resources.schools_app.core.myTypes.UserType
import com.smart.resources.schools_app.features.users.data.TeacherModel

// TODO: make it suspend
interface IGetCurrentUserTypeUseCase{
   operator fun invoke(): UserType
}

// TODO: make it suspend
interface IGetCurrentTeacherModelUseCase{
   operator fun invoke(): TeacherModel?
}

interface IIsUserLoggedUseCase{
   suspend operator fun invoke(): Boolean
}

interface IGetUserIdUseCase{
   suspend operator fun invoke(): String
}

interface IGetFirstStudentLoginUseCase{
   suspend operator fun invoke(): Boolean
}

interface IGetFirstTeacherLoginUseCase{
   suspend operator fun invoke(): Boolean
}

interface ISetNotFirstStudentLoginUseCase{
   suspend operator fun invoke()
}

interface ISetNotFirstTeacherLoginUseCase{
   suspend operator fun invoke()
}