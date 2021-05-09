package com.rafalbiarda.medcinedontforgetversion8.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Disease(
    var name: String ="",
    var symptoms: String = ""
) : Parcelable