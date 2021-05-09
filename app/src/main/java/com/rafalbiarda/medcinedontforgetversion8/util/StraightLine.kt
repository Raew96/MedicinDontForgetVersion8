package com.rafalbiarda.medcinedontforgetversion8.util

import android.content.Context
import android.widget.LinearLayout
import com.rafalbiarda.medcinedontforgetversion8.R

class StraightLine(context: Context) : LinearLayout(context){

    init {
        inflate(context, R.layout.straight_line, this)
    }
}