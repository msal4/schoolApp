package com.smart.resources.schools_app.features.users.domain.usecase

import com.hadiyarajesh.flower.Resource
import com.smart.resources.schools_app.core.myTypes.UserType
import com.smart.resources.schools_app.features.onlineExam.domain.model.OnlineExam
import kotlinx.coroutines.flow.Flow

interface IGetCurrentUserTypeUseCase{
   operator fun invoke(): UserType
}
