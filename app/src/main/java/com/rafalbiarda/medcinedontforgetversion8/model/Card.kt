package com.rafalbiarda.medcinedontforgetversion8.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*
import kotlin.collections.ArrayList

@Parcelize
data class Card(
    var date: Date? = null,
    var id: String = "",
    var medicineReminderList: List<MedicineReminder> = ArrayList(),
    var health: Health = Health()

) : Parcelable
