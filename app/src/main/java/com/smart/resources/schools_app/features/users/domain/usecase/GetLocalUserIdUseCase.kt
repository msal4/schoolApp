package com.smart.resources.schools_app.features.users.domain.usecase

import com.smart.resources.schools_app.features.users.data.UserRepository
import javax.inject.Inject

class GetLocalUserIdUseCase @Inject constructor(private val userRepository: UserRepository) :
    IGetCurrentUserIdUseCase {
    override suspend fun invoke(): String {
        return userRepository.getCurrentUserAccount()?.uid.toString()
    }

}