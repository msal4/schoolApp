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

interface IGetUserIdUseCase{
   suspend operator fun invoke(): String
}
