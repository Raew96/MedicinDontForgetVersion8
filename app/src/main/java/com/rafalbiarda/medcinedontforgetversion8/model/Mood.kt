package com.rafalbiarda.medcinedontforgetversion8.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Mood(
    var time: String ="",
    var value: Int = 0
) : Parcelable