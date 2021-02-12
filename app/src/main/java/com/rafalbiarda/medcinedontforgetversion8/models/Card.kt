package com.rafalbiarda.medcinedontforgetversion8.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Card(
    val date: Date? = null,
    val id: String = "",
    var medicineReminderList: List<MedicineReminder> = ArrayList()
) : Parcelable
