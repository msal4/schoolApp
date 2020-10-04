package com.smart.resources.schools_app.features.users.domain.usecase

import com.smart.resources.schools_app.features.users.data.TeacherModel
import com.smart.resources.schools_app.features.users.data.UserRepository
import javax.inject.Inject

class GetCurrentTeacherModelUseCase @Inject constructor(private val userRepository: UserRepository) :
    IGetCurrentTeacherModelUseCase {
    override fun invoke(): TeacherModel? {
        val userModel= userRepository.getUser()
        return if (userModel !=null && userModel is TeacherModel) userModel
        else null
    }
}