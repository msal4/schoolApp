package com.smart.resources.schools_app.features.filterSheet

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FilterItem(
    val selected:Boolean= false,
    val item:String,
):Parcelable