package com.example.jobeasy.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class JobTag(
    val value: String,
    val bg_color: String,
    val text_color: String
) : Parcelable
