package com.rafalbiarda.medcinedontforgetversion8.util

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.TextView
import com.rafalbiarda.medcinedontforgetversion8.R
import kotlinx.android.synthetic.main.benefit_view.view.*
import kotlinx.android.synthetic.main.benefit_view2.view.*
import org.w3c.dom.Text

class BenefitView2(context: Context) : LinearLayout(context){

    var tvName: TextView
    var tvHistory: TextView

    init {
        inflate(context, R.layout.benefit_view2, this)

        tvName = tv_item_medicine_name
        tvHistory = tv_item_medicine_history
    }

    fun setCap(name : String, history : String)
    {
        tvName.text = name
        tvHistory.text = history
    }
}