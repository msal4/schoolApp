package com.smart.resources.schools_app.features.users.domain.usecase

import com.smart.resources.schools_app.core.appDatabase.storages.SharedPrefHelper
import javax.inject.Inject

class GetFirstStudentLoginUseCase @Inject constructor(
    private val sharedPrefHelper: SharedPrefHelper) :
    IGetFirstStudentLoginUseCase {
    override suspend fun invoke(): Boolean {
        return sharedPrefHelper.firstStudentLogin
    }
}