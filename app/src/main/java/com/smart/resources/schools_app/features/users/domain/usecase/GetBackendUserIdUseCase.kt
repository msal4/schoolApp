package com.smart.resources.schools_app.features.users.domain.usecase

import com.smart.resources.schools_app.features.users.data.repository.UserRepository
import javax.inject.Inject

class GetBackendUserIdUseCase @Inject constructor(private val userRepository: UserRepository) :
    IGetCurrentBackendUserIdUseCase {
    override suspend fun invoke(): String {
        return userRepository.getCurrentUser()?.backendUserId.toString()
    }
}