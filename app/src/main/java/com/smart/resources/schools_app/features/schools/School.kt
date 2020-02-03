package com.smart.resources.schools_app.features.schools
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class School(
    @PrimaryKey val schoolId: String,
    val name: String,
    @SerializedName("image")
    val logo: String,
    val baseUrl: String):Parcelable