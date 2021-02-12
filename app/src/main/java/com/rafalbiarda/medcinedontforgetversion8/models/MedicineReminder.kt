package com.rafalbiarda.medcinedontforgetversion8.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.sql.Timestamp
import java.util.*

@Parcelize
class MedicineReminder(
    var date: Date? = null ,
    var id: String = "",
    var medicine: Medicine = Medicine()
): Parcelable