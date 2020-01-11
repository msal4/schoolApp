package com.smart.resources.schools_app.features.homework

import com.smart.resources.schools_app.core.adapters.dateDisFormatter
import com.smart.resources.schools_app.core.helpers.BackendHelper
import com.smart.resources.schools_app.core.utils.asBodyPart
import com.smart.resources.schools_app.core.utils.asRequestBody
import retrofit2.Response

object HomeworkRepository{

    suspend fun addHomework(postHomeworkModel: PostHomeworkModel): Response<Unit>? {
        postHomeworkModel.apply {
                date?.format(dateDisFormatter)?.asRequestBody()?.let {
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