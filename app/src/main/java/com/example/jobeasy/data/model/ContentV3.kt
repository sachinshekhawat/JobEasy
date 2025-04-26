package com.example.jobeasy.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ContentV3(
    val V3: List<ContentField>
) : Parcelable
