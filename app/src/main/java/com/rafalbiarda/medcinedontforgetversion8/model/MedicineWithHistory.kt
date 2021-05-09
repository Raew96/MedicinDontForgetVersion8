package com.rafalbiarda.medcinedontforgetversion8.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*
import kotlin.collections.ArrayList

@Parcelize
class MedicineWithHistory(
    var name: String = "",
    var history: List<Date> = ArrayList(),
) : Parcelable
