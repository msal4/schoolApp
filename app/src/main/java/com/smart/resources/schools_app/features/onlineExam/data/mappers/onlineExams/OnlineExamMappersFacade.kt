package com.smart.resources.schools_app.features.onlineExam.data.mappers.onlineExams

import com.smart.resources.schools_app.features.onlineExam.data.local.model.LocalOnlineExam
import com.smart.resources.schools_app.features.onlineExam.data.remote.model.NetworkOnlineExam
import com.smart.resources.schools_app.features.onlineExam.domain.model.OnlineExam

class OnlineExamMappersFacade(
    private val localOnlineExamsMapper: (List<LocalOnlineExam>) -> List<OnlineExam>,
    private val networkOnlineExamsMapper: (List<NetworkOnlineExam>?) -> List<OnlineExam>,
    private val onlineExamsMapper: (List<OnlineExam>) -> List<LocalOnlineExam>
) {

    fun mapLocalOnlineExams(onlineExams: List<LocalOnlineExam>) = localOnlineExamsMapper(onlineExams)
    fun mapOnlineExams(onlineExams: List<OnlineExam>) = onlineExamsMapper(onlineExams)
    private fun mapNetworkOnlineExams(onlineExams: List<NetworkOnlineExam>?) = networkOnlineExamsMapper(onlineExams)

    fun mapNetworkToLocalOnlineExams(onlineExams: List<NetworkOnlineExam>):List<LocalOnlineExam>{
        val domainOnlineExams= mapNetworkOnlineExams(onlineExams)
        return mapOnlineExams(domainOnlineExams)
    }
}