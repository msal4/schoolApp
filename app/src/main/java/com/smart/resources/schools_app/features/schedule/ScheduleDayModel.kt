package com.smart.resources.schools_app.features.schedule

import android.os.Parcel
import android.os.Parcelable

data class ScheduleDayModel(
    val day: String,
    val dayList: List<String?>
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.createStringArrayList()?: listOf()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(day)
        parcel.writeStringList(dayList)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ScheduleDayModel> {
        override fun createFromParcel(parcel: Parcel): ScheduleDayModel {
            return ScheduleDayModel(
                parcel
            )
        }

        override fun newArray(size: Int): Array<ScheduleDayModel?> {
            return arrayOfNulls(size)
        }
    }
}