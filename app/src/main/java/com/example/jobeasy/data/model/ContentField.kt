package com.example.jobeasy.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class ContentField(
    val field_key: String = "",
    val field_name: String = "",
    val field_value: String = ""
) : Parcelable