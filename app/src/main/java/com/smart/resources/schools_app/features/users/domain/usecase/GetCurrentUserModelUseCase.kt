package com.smart.resources.schools_app.features.users.domain.usecase

import com.smart.resources.schools_app.features.users.data.UserModel
import com.smart.resources.schools_app.features.users.data.UserRepository
import javax.inject.Inject

class GetCurrentUserModelUseCase @Inject constructor(private val userRepository: UserRepository) :
    IGetCurrentUserModelUseCase {
    override suspend fun invoke(): UserModel? {
        return userRepository.getUser()
    }
}