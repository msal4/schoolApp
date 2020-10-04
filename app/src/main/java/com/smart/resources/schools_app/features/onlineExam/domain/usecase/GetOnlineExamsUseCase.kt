package com.smart.resources.schools_app.features.onlineExam.domain.usecase

import com.hadiyarajesh.flower.Resource
import com.smart.resources.schools_app.core.myTypes.UserType
import com.smart.resources.schools_app.features.onlineExam.domain.model.OnlineExam
import com.smart.resources.schools_app.features.onlineExam.domain.repository.IOnlineExamsRepository
import com.smart.resources.schools_app.features.users.data.UserRepository
import com.smart.resources.schools_app.features.users.domain.usecase.GetCurrentUserTypeUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetOnlineExamsUseCase @Inject constructor(
    private val onlineExamsRepository: IOnlineExamsRepository,
    private val userRepository: UserRepository,
) : IGetOnlineExamsUseCase {
    override operator fun invoke(): Flow<Resource<List<OnlineExam>>> {
        val userId =
            userRepository.getCurrentUserAccount()?.uid.toString() // TODO: change this to usecase

        return onlineExamsRepository.getOnlineExams(userId)
    }
}