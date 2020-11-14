package com.smart.resources.schools_app.features.users.domain.usecase

import com.smart.resources.schools_app.features.users.data.repository.UserRepository
import javax.inject.Inject

class IsUserLoggedUseCase @Inject constructor(private val userRepository: UserRepository) :
    IIsUserLoggedUseCase {
    override suspend fun invoke(): Boolean {
        return userRepository.getCurrentAccount()?.userType != null
    }
}