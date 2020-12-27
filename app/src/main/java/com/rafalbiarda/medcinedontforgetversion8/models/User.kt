package com.rafalbiarda.medcinedontforgetversion8.models

import android.os.Parcelable
import com.google.common.primitives.UnsignedInteger
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val id: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val image: String = "",
    val mobile: Long = 0,
    val gender: String = "",
    val profileCompleted: Int = 0,
    val pin: Int = 0,
    val dateOfBirth:String = ""
    ): Parcelable