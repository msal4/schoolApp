package com.smart.resources.schools_app.features.onlineExam.data.local.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.smart.resources.schools_app.features.users.data.model.Account

data class UserWithOnlineExams(
    @Embedded val account: Account,
    @Relation(
        parentColumn = "userId",
        entityColumn = "onlineExamId",
        associateBy = Junction(UserOnlineExamCrossRef::class),
    )
    val onlineExams: List<LocalOnlineExam>
)