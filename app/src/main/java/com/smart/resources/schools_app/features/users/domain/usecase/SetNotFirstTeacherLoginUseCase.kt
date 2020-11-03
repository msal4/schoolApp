package com.smart.resources.schools_app.features.users.domain.usecase

import com.smart.resources.schools_app.core.appDatabase.storages.SharedPrefHelper
import javax.inject.Inject

class SetNotFirstTeacherLoginUseCase @Inject constructor(
    private val sharedPrefHelper: SharedPrefHelper) :
    ISetNotFirstTeacherLoginUseCase {
    override suspend fun invoke() {
         sharedPrefHelper.firstTeacherLogin= false
    }
}