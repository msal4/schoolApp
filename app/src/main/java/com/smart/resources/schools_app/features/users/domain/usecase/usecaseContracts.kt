package com.smart.resources.schools_app.features.users.domain.usecase

import com.smart.resources.schools_app.core.myTypes.UserType
import com.smart.resources.schools_app.features.users.data.model.userInfo.TeacherInfoModel
import com.smart.resources.schools_app.features.users.data.model.userInfo.UserInfoModel

interface IGetCurrentUserTypeUseCase{
   suspend operator fun invoke(): UserType
}

interface IGetCurrentUserModelUseCase{
   suspend operator fun invoke(): UserInfoModel?
}

interface IDeleteUserUseCase{
   suspend operator fun invoke(userId:String): Unit
}

interface IGetCurrentTeacherModelUseCase{
   suspend operator fun invoke(): TeacherInfoModel?
}

interface IIsUserLoggedUseCase{
   suspend operator fun invoke(): Boolean
}

interface IGetCurrentLocalUserIdUseCase{
   suspend operator fun invoke(): String
}

interface IGetCurrentBackendUserIdUseCase{
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