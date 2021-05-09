package com.rafalbiarda.medcinedontforgetversion8.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Doctor(
    val image: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val mobile: Long = 0,
    val specialization: String = ""
) : Parcelable
