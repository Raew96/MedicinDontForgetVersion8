package com.rafalbiarda.medcinedontforgetversion8.models

import android.graphics.Bitmap
import java.time.temporal.TemporalAmount

class Medicine(
    var img: Bitmap? = null,
    var name: String,
    var producerName: String,
    var medicine_instructions: String,
    var amount: Int,
    var dose: Int,
    var doseType: String,
)
