package com.example.littlefoxproject.first.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VideoPicture(
    val id: Int,
    val nr: Int,
    val picture: String
): Parcelable