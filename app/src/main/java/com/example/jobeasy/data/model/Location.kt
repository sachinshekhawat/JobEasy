package com.example.jobeasy.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Location(
    val id: Int,
    val locale: String,
    val state: Int
) : Parcelable