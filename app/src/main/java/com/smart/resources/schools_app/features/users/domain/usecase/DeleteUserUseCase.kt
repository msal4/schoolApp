package com.smart.resources.schools_app.features.users.domain.usecase

import com.smart.resources.schools_app.features.users.data.repository.UserRepository
import javax.inject.Inject

class DeleteUserUseCase @Inject constructor(private val userRepository: UserRepository) :
    IDeleteUserUseCase {
    override suspend fun invoke(userId: String) {
        userRepository.deleteAccount(userId)
    }

}