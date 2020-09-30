package com.smart.resources.schools_app.features.users.domain.usecase

import com.smart.resources.schools_app.features.users.data.TeacherModel
import com.smart.resources.schools_app.features.users.data.UserRepository
import javax.inject.Inject

class GetCurrentTeacherDataUseCase @Inject constructor(private val userRepository: UserRepository) :
    IGetCurrentTeacherDataUseCase {
    override fun invoke(): TeacherModel? {
        val userModel= userRepository.getUser()
        return if (userModel !=null && userModel is TeacherModel) userModel
        else null
    }
}