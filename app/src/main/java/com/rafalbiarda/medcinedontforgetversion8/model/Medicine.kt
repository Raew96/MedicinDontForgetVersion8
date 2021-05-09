package com.rafalbiarda.medcinedontforgetversion8.model

import android.graphics.Bitmap
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Medicine(
    var img: Bitmap? = null,
    var name: String = "",
    var producerName: String = "",
    var medicine_instructions: String = "",
    var amount: Int = 0,
    var doseType: String = "",
) : Parcelable
