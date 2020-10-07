package com.smart.resources.schools_app.features.onlineExam.domain.usecase

import com.hadiyarajesh.flower.Resource
import com.smart.resources.schools_app.features.onlineExam.data.repository.QuestionsRepository
import com.smart.resources.schools_app.features.onlineExam.domain.model.Question
import com.smart.resources.schools_app.features.onlineExam.domain.model.onlineExam.OnlineExam
import com.smart.resources.schools_app.features.onlineExam.domain.repository.IOnlineExamsRepository
import com.smart.resources.schools_app.features.onlineExam.domain.repository.IQuestionsRepository
import com.smart.resources.schools_app.features.users.data.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetOnlineExamQuestionsUseCase @Inject constructor(
    private val questionsRepository: IQuestionsRepository
) : IGetOnlineExamQuestionsUseCase {
    override  fun invoke(examId:String): Flow<Resource<List<Question>>> {
        return questionsRepository.getExamQuestions(examId)
    }
}