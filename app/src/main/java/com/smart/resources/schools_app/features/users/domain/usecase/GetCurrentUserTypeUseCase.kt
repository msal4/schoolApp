package com.smart.resources.schools_app.features.users.domain.usecase

import com.smart.resources.schools_app.core.myTypes.UserType
import com.smart.resources.schools_app.features.users.data.repository.UserRepository
import javax.inject.Inject

class GetCurrentUserTypeUseCase @Inject constructor(private val userRepository: UserRepository) :
    IGetCurrentUserTypeUseCase {
    override suspend fun invoke(): UserType {
        return if (userRepository.getCurrentAccount()?.userType == 0) UserType.STUDENT else UserType.TEACHER
    }
}