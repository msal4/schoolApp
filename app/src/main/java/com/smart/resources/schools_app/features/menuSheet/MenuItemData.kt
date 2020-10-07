package com.smart.resources.schools_app.features.menuSheet

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MenuItemData(
    @StringRes val label:Int,
    @DrawableRes val iconDrawable:Int
):Parcelable