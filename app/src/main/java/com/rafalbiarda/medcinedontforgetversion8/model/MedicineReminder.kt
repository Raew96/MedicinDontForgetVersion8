package com.rafalbiarda.medcinedontforgetversion8.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
class MedicineReminder(
    var date: Date? = null ,
    var id: String = "",
    var medicine: Medicine = Medicine(),
    var isTaken: Boolean = false
): Parcelable