package com.smart.resources.schools_app.features.onlineExam.domain.usecase

import com.hadiyarajesh.flower.Resource
import com.smart.resources.schools_app.core.myTypes.UserType
import com.smart.resources.schools_app.features.onlineExam.domain.model.onlineExam.OnlineExam
import com.smart.resources.schools_app.features.onlineExam.domain.repository.IOnlineExamsRepository
import com.smart.resources.schools_app.features.users.data.repository.UserRepository
import com.smart.resources.schools_app.features.users.domain.usecase.GetCurrentUserTypeUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetUserOnlineExamsUseCase @Inject constructor(
    private val onlineExamsRepository: IOnlineExamsRepository,
    private val getCurrentUserTypeUseCase: GetCurrentUserTypeUseCase,
    private val userRepository: UserRepository,
    ) : IGetUserOnlineExamsUseCase {
    override operator fun invoke(): Flow<Resource<List<OnlineExam>>> {
        return flow {
           emit(Resource.loading())
            val userId = userRepository.getCurrentAccount()?.userId.toString()
            val isTeacher= getCurrentUserTypeUseCase() == UserType.TEACHER

            emitAll(onlineExamsRepository.getUserOnlineExams(userId, isTeacher))
        }
    }
}