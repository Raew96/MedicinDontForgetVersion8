package com.rafalbiarda.medcinedontforgetversion8.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Health (
    var mood : List<Mood>  = ArrayList(),
    var sleep : String = "",
    var diseases : List<Disease> = ArrayList()
) : Parcelable
