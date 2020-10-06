package com.smart.resources.schools_app.features.onlineExam.data.mappers.onlineExams

import com.smart.resources.schools_app.core.utils.mapList
import com.smart.resources.schools_app.features.onlineExam.data.local.model.LocalOnlineExam
import com.smart.resources.schools_app.features.onlineExam.data.remote.model.onlineExam.NetworkOnlineExam
import com.smart.resources.schools_app.features.onlineExam.data.remote.model.onlineExam.PostNetworkOnlineExam
import com.smart.resources.schools_app.features.onlineExam.domain.model.onlineExam.OnlineExam
import com.smart.resources.schools_app.features.onlineExam.domain.model.onlineExam.AddOnlineExam

class OnlineExamMappersFacade(
    private val localOnlineExamMapper: (LocalOnlineExam) -> OnlineExam,
    private val networkOnlineExamMapper: (NetworkOnlineExam) -> OnlineExam,
    private val onlineExamToLocalMapper: (OnlineExam) -> LocalOnlineExam,
    private val addOnlineExamToNetworkMapper: (AddOnlineExam) -> PostNetworkOnlineExam
) {

    fun mapLocalOnlineExam(input: LocalOnlineExam):OnlineExam = localOnlineExamMapper(input)
    fun mapNetworkOnlineExam(input: NetworkOnlineExam):OnlineExam  = networkOnlineExamMapper(input)
    fun mapOnlineExamToLocal(input: OnlineExam):LocalOnlineExam  = onlineExamToLocalMapper(input)
    fun mapOnlineExamAddToNetwork(input: AddOnlineExam) : PostNetworkOnlineExam = addOnlineExamToNetworkMapper(input)
    fun mapNetworkToLocalOnlineExam(input: NetworkOnlineExam): LocalOnlineExam = mapOnlineExamToLocal(mapNetworkOnlineExam(input))


    fun mapLocalOnlineExam(input: List<LocalOnlineExam>): List<OnlineExam> = mapList(
        input = input,
        mapSingle = localOnlineExamMapper
    )
    fun mapNetworkOnlineExam(input: List<NetworkOnlineExam>): List<OnlineExam> = mapList(
        input = input,
        mapSingle = networkOnlineExamMapper
    )
    fun mapOnlineExamToLocal(input: List<OnlineExam>): List<LocalOnlineExam> = mapList(
        input = input,
        mapSingle = onlineExamToLocalMapper
    )
    fun mapOnlineExamAddToNetwork(input: List<AddOnlineExam>): List<PostNetworkOnlineExam> = mapList(
        input = input,
        mapSingle = addOnlineExamToNetworkMapper
    )
    fun mapNetworkToLocalOnlineExam(input: List<NetworkOnlineExam>): List<LocalOnlineExam> = mapList(
        input = input,
        mapSingle = ::mapNetworkToLocalOnlineExam
    )
}