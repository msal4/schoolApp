package com.smart.resources.schools_app.features.users.domain.usecase

import com.smart.resources.schools_app.core.appDatabase.storages.SharedPrefHelper
import javax.inject.Inject

class SetNotFirstStudentLoginUseCase @Inject constructor(
    private val sharedPrefHelper: SharedPrefHelper) :
    ISetNotFirstStudentLoginUseCase {
    override suspend fun invoke() {
        sharedPrefHelper.firstStudentLogin= false
    }
}