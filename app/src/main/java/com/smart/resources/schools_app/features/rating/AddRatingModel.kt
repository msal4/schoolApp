package com.smart.resources.schools_app.features.rating

import android.os.Parcel
import android.os.Parcelable
import com.smart.resources.schools_app.features.students.Student
import org.threeten.bp.LocalDateTime


open class RatingModel (
    var stars: Float,
    var note: String,
    var date: LocalDateTime
){
    override fun toString(): String {
        return "RatingModel(stars=$stars, note='$note', date=$date)"
    }
}

class AddRatingModel  (
    val studentId: String,
    @Transient val studentName: String,
    stars: Float= -1.0f,
    note: String= "",
    date: LocalDateTime= LocalDateTime.now()
): RatingModel(stars, note, date), Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()?: "",
        parcel.readString()?: "",
        parcel.readFloat(),
        parcel.readString()?: "",
        parcel.readSerializable() as LocalDateTime
    )

    constructor(student: Student):this(student.idStudent, student.name)

    val isRated get() =  stars > -1.0F
    fun reset(){
        stars= -1.0F
        note= ""
        date= LocalDateTime.now()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(studentId)
        parcel.writeString(studentName)
        parcel.writeFloat(stars)
        parcel.writeString(note)
        parcel.writeSerializable(date)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "${super.toString()}\nAddRatingModel(studentId='$studentId', studentName='$studentName')"
    }


    companion object CREATOR : Parcelable.Creator<AddRatingModel> {
        override fun createFromParcel(parcel: Parcel): AddRatingModel {
            return AddRatingModel(parcel)
        }

        override fun newArray(size: Int): Array<AddRatingModel?> {
            return arrayOfNulls(size)
        }
    }
}