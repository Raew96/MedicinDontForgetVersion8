package com.rafalbiarda.medcinedontforgetversion8.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.time.Year
import java.util.*
import kotlin.collections.ArrayList

@Parcelize
data class MonthlyStatisticsCard(
    var id: String = "",
    var monthOfYear: Int = 0,
    var ratingsOfMood : List<Int> = arrayListOf(),
    var ratingsOfSleep : List<Int> = arrayListOf(),
    var diseasesWithDate : List<DiseaseWithDate> = arrayListOf(),
    var medicineWithHistory: List<MedicineWithHistory> = arrayListOf()
) : Parcelable
