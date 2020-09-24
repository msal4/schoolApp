package com.smart.resources.schools_app.features.students

class StudentWithMark(
    val mark:Int?, // TODO: remove mark !!!!
    val idStudent: String,
    val name: String
)

class SendStudentResult(
    val marks: List<Marks>,
    val examId: Int
)
class Marks(
    val mark: Int,
    val studentId: String
)
