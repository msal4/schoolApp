package com.smart.resources.schools_app.features.users.domain.usecase

import com.smart.resources.schools_app.core.appDatabase.storages.SharedPrefHelper
import javax.inject.Inject

class GetFirstTeacherLoginUseCase @Inject constructor(
    private val sharedPrefHelper: SharedPrefHelper) :
    IGetFirstTeacherLoginUseCase {
    override suspend fun invoke(): Boolean {
        return sharedPrefHelper.firstTeacherLogin
    }
}