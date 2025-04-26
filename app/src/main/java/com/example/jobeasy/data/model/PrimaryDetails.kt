package com.example.jobeasy.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PrimaryDetails(
    val Place: String = "",
    val Salary: String = "",
    val Job_Type: String = "",
    val Experience: String = "",
    val Fees_Charged: String = "",
    val Qualification: String = ""
) : Parcelable

