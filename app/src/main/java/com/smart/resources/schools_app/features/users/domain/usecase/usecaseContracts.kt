package com.smart.resources.schools_app.features.users.domain.usecase

import com.smart.resources.schools_app.core.myTypes.UserType
import com.smart.resources.schools_app.features.users.data.TeacherModel
import com.smart.resources.schools_app.features.users.data.UserModel

interface IGetCurrentUserTypeUseCase{
   suspend operator fun invoke(): UserType
}

interface IGetCurrentUserModelUseCase{
   suspend operator fun invoke(): UserModel?
}

interface IDeleteUserUseCase{
   suspend operator fun invoke(userId:String): Unit
}

interface IGetCurrentTeacherModelUseCase{
   suspend operator fun invoke(): TeacherModel?
}

interface IIsUserLoggedUseCase{
   suspend operator fun invoke(): Boolean
}

interface IGetCurrentUserIdUseCase{
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