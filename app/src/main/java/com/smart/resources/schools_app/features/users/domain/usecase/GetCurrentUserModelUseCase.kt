package com.smart.resources.schools_app.features.users.domain.usecase

import com.smart.resources.schools_app.features.users.data.model.userInfo.UserInfoModel
import com.smart.resources.schools_app.features.users.data.repository.UserRepository
import javax.inject.Inject

class GetCurrentUserModelUseCase @Inject constructor(private val userRepository: UserRepository) :
    IGetCurrentUserModelUseCase {
    override suspend fun invoke(): UserInfoModel? {
        return userRepository.getUserModel()
    }
}