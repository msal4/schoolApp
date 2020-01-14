package com.smart.resources.schools_app.features.homework

import com.smart.resources.schools_app.core.adapters.dateTimeBackendSendFormatter
import com.smart.resources.schools_app.core.helpers.BackendHelper
import com.smart.resources.schools_app.core.utils.asBodyPart
import com.smart.resources.schools_app.core.utils.asRequestBody
import retrofit2.Response

object HomeworkRepository{

    suspend fun addHomework(postHomeworkModel: PostHomeworkModel): Response<HomeworkModel>? {
        postHomeworkModel.apply {
                date?.format(dateTimeBackendSendFormatter)?.asRequestBody()?.let {
                    formattedDate->

                    return BackendHelper.homeworkDao.addHomework(
                        subjectName.asRequestBody(),
                        assignmentName.asRequestBody(),
                        formattedDate,
                        note.asRequestBody(),
                        attachment?.asBodyPart("attachment"),
                        classId.asRequestBody()
                    )
                }

                return null
            }
    }
}