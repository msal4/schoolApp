package com.smart.resources.schools_app.features.students

import android.os.Parcel
import android.os.Parcelable

data class Student(
class Student(
    val mark:Int?,
    val idStudent: String,
    val name: String
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()?: "",
        parcel.readString()?:""
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(idStudent)
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Student> {
        override fun createFromParcel(parcel: Parcel): Student {
            return Student(parcel)
        }

        override fun newArray(size: Int): Array<Student?> {
            return arrayOfNulls(size)
        }
    }
}

class SendStudentResult(
    val marks: List<Marks>,
    val examId: Int
)
class Marks(
    val mark: Int,
    val studentId: String
)
)
class SendStudentResult(
    val marks: List<Marks>,
    val examId: Int
)
class Marks(
    val mark: Int,
    val studentId: String
)