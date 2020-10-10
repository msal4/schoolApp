package com.smart.resources.schools_app.features.onlineExam.domain.usecase.answers

import com.hadiyarajesh.flower.Resource
import com.smart.resources.schools_app.features.onlineExam.domain.model.BaseAnswer
import com.smart.resources.schools_app.features.onlineExam.domain.repository.IAnswersRepository
import com.smart.resources.schools_app.features.onlineExam.domain.usecase.IGetStudentExamAnswersUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetStudentExamAnswersUseCase @Inject constructor(
    private val answersRepository: IAnswersRepository
) : IGetStudentExamAnswersUseCase {
    override fun invoke(examId: String, studentId: String): Flow<Resource<List<BaseAnswer<Any>>>> {
        return answersRepository.getStudentExamAnswers(examId, studentId)
    }
}