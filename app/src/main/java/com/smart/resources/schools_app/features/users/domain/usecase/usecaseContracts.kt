package com.smart.resources.schools_app.features.users.domain.usecase

import com.smart.resources.schools_app.core.myTypes.UserType
import com.smart.resources.schools_app.features.users.data.TeacherModel

interface IGetCurrentUserTypeUseCase{
   operator fun invoke(): UserType
}

interface IGetCurrentTeacherDataUseCase{
   operator fun invoke(): TeacherModel?
}

