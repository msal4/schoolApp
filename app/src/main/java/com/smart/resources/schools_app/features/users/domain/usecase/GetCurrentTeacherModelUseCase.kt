package com.smart.resources.schools_app.features.users.domain.usecase

import com.smart.resources.schools_app.features.users.data.model.userInfo.TeacherInfoModel
import com.smart.resources.schools_app.features.users.data.repository.UserRepository
import javax.inject.Inject

class GetCurrentTeacherModelUseCase @Inject constructor(private val userRepository: UserRepository) :
    IGetCurrentTeacherModelUseCase {
    override suspend fun invoke(): TeacherInfoModel? {
        val userModel= userRepository.getUserModel()
        return if (userModel !=null && userModel is TeacherInfoModel) userModel
        else null
    }
}