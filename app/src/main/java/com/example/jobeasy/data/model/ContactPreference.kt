package com.example.jobeasy.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ContactPreference(
    val preference: Int = 0,
    val whatsapp_link: String = "",
    val preferred_call_start_time: String = "",
    val preferred_call_end_time: String = ""
) : Parcelable

