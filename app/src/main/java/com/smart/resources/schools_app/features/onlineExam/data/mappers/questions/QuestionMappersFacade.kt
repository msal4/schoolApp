package com.smart.resources.schools_app.features.onlineExam.data.mappers.questions

import com.smart.resources.schools_app.core.utils.mapList
import com.smart.resources.schools_app.features.onlineExam.data.local.model.LocalQuestion
import com.smart.resources.schools_app.features.onlineExam.data.remote.model.NetworkQuestion
import com.smart.resources.schools_app.features.onlineExam.domain.model.Question

class QuestionMappersFacade(
    private val localQuestionMapper: (LocalQuestion) -> Question,
    private val networkQuestionMapper: (NetworkQuestion) -> Question,
    private val questionsToLocalMapper: (Question, String) -> LocalQuestion,
    private val questionsToNetworkMapper: (Question, String) -> NetworkQuestion
) {

    fun mapLocalQuestion(input: LocalQuestion): Question = localQuestionMapper(input)
    fun mapNetworkQuestion(input: NetworkQuestion): Question = networkQuestionMapper(input)
    fun mapQuestionToLocal(input: Question, examId:String): LocalQuestion = questionsToLocalMapper(input, examId)
    fun mapQuestionToNetwork(input: Question, examId:String): NetworkQuestion = questionsToNetworkMapper(input, examId)
    fun mapNetworkToLocalQuestion(input: NetworkQuestion): LocalQuestion {
        val domainOnlineExams = mapNetworkQuestion(input)
        return mapQuestionToLocal(domainOnlineExams, input.onlineExamId.toString())
    }

    fun mapLocalQuestion(input: List<LocalQuestion>): List<Question> = mapList(
        input = input,
        mapSingle = localQuestionMapper,
    )
    fun mapNetworkQuestion(input: List<NetworkQuestion>): List<Question> = mapList(
        input = input,
        mapSingle = networkQuestionMapper,
    )
    fun mapQuestionToLocal(input: List<Question>, examId:String): List<LocalQuestion> = mapList(
        input = input,
        mapSingle = {
            questionsToLocalMapper(it, examId)
        },
    )
    fun mapQuestionToNetwork(input: List<Question>, examId:String): List<NetworkQuestion> = mapList(
        input = input,
        mapSingle = {
            questionsToNetworkMapper(it, examId)
        },
    )

    fun mapNetworkToLocalQuestion(input: List<NetworkQuestion>): List<LocalQuestion> = mapList(
        input= input,
        mapSingle = ::mapNetworkToLocalQuestion
    )
}