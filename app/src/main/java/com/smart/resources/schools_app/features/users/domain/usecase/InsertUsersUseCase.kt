package com.smart.resources.schools_app.features.users.domain.usecase

import com.smart.resources.schools_app.features.users.data.model.User
import com.smart.resources.schools_app.features.users.data.repository.UserRepository
import javax.inject.Inject

class InsertUsersUseCase @Inject constructor(private val userRepository: UserRepository) :
    IInsertUsersUseCase {
    override suspend fun invoke(users: List<User>) {
        userRepository.insertUsers(users)
    }
}