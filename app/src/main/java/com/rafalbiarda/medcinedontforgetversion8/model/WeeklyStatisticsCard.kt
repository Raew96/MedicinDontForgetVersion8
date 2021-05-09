package com.rafalbiarda.medcinedontforgetversion8.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*
import kotlin.collections.ArrayList

@Parcelize
data class WeeklyStatisticsCard(
    var id: String = "",
    var weekOfYear: Int = 0,
    var ratingsOfMood : List<Int> = arrayListOf(),
    var ratingsOfSleep : List<Int> = arrayListOf(),
    var diseasesWithDate : List<DiseaseWithDate> = arrayListOf(),
    var medicineWithHistory: List<MedicineWithHistory> = arrayListOf()

) : Parcelable
