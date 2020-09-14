package com.smart.resources.schools_app.features.homework

import com.smart.resources.schools_app.features.homeworkSolution.data.model.HomeworkSolutionModel

data class HomeworkWithSolution (
    val homework:HomeworkModel,
    val solution:HomeworkSolutionModel?
)