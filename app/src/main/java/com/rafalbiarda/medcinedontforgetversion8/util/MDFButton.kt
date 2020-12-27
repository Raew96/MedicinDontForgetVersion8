package com.rafalbiarda.medcinedontforgetversion8.util

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.widget.TextViewCompat
import com.google.android.material.textfield.TextInputEditText

class MDFButton(context: Context, attributeSet: AttributeSet) :
    AppCompatButton(context, attributeSet) {

    init {
            applyFont()
    }

    private fun applyFont() {
        val typeface = Typeface.createFromAsset(context.assets, "Montserrat-Bold.ttf")
        setTypeface(typeface)
    }
}