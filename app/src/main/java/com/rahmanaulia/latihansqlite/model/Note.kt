package com.rahmanaulia.latihansqlite.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Note(
    var id: Int? = 0,
    var title: String? = null,
    var description: String? = null
): Parcelable