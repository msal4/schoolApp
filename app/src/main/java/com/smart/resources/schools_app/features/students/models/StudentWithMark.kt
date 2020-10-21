package com.smart.resources.schools_app.features.students.models

class StudentWithMark(
    val mark: Int?,
    id: String,
    name: String,
) : Student(id, name)

class SendStudentResult(
    val marks: List<Marks>,
    val examId: Int
)

class Marks(
    val mark: Int,
    val studentId: String
)
