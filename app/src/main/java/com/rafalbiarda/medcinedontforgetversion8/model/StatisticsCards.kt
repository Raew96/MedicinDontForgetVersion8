package com.rafalbiarda.medcinedontforgetversion8.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*
import kotlin.collections.ArrayList

@Parcelize
data class StatisticsCards(
    var id: String = "",
    var weeklyStatisticsList: List<WeeklyStatisticsCard> = ArrayList(),
    var monthlyStatisticsList: List<MonthlyStatisticsCard> = ArrayList(),
) : Parcelable
