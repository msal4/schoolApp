package com.smart.resources.schools_app.features.onlineExam.domain.usecase

import com.hadiyarajesh.flower.Resource
import com.smart.resources.schools_app.features.onlineExam.domain.model.Question
import com.smart.resources.schools_app.features.onlineExam.domain.repository.IQuestionsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetExamQuestionsUseCase @Inject constructor(
    private val questionsRepository: IQuestionsRepository
) : IGetExamQuestionsUseCase {
    override  fun invoke(examId:String): Flow<Resource<List<Question>>> {
        return questionsRepository.getExamQuestions(examId)
    }
}