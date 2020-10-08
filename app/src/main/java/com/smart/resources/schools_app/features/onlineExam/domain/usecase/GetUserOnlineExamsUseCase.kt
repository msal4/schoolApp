package com.smart.resources.schools_app.features.onlineExam.domain.usecase

import com.hadiyarajesh.flower.Resource
import com.smart.resources.schools_app.features.onlineExam.domain.model.onlineExam.OnlineExam
import com.smart.resources.schools_app.features.onlineExam.domain.repository.IOnlineExamsRepository
import com.smart.resources.schools_app.features.users.data.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserOnlineExamsUseCase @Inject constructor(
    private val onlineExamsRepository: IOnlineExamsRepository,
    private val userRepository: UserRepository,
) : IGetUserOnlineExamsUseCase {
    override operator fun invoke(): Flow<Resource<List<OnlineExam>>> {
        val userId =
            userRepository.getCurrentUserAccount()?.uid.toString() // TODO: change this to usecase

        return onlineExamsRepository.getUserOnlineExams(userId)
    }
}