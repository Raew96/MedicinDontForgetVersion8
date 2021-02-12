package com.rafalbiarda.medcinedontforgetversion8.models

import android.graphics.Bitmap
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.time.temporal.TemporalAmount

@Parcelize
class Medicine(
    var img: Bitmap? = null,
    var name: String = "",
    var producerName: String = "",
    var medicine_instructions: String = "",
    var amount: Int = 0,
    var doseType: String = "",
) : Parcelable
