package com.rafalbiarda.medcinedontforgetversion8.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
class DiseaseWithDate(
    var name: String ="",
    var symptoms: String = "",
    var date: Date? = null
) : Parcelable