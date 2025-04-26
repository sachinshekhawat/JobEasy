package com.example.jobeasy.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Creative(
    val file: String,
    val thumb_url: String,
    val creative_type: Int
) : Parcelable
