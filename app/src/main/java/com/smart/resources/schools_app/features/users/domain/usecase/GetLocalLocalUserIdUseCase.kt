package com.smart.resources.schools_app.features.users.domain.usecase

import com.smart.resources.schools_app.features.users.data.UserRepository
import javax.inject.Inject

class GetLocalLocalUserIdUseCase @Inject constructor(private val userRepository: UserRepository) :
    IGetUserIdUseCase {
    override suspend fun invoke(): String {
        return userRepository.getCurrentUserAccount()?.uid.toString()
    }

}