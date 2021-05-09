package com.rafalbiarda.medcinedontforgetversion8.util

import android.content.Context
import android.widget.LinearLayout
import android.widget.TextView
import com.rafalbiarda.medcinedontforgetversion8.R
import kotlinx.android.synthetic.main.benefit_view.view.*

class BenefitView(context: Context) : LinearLayout(context){

    private var tvName: TextView
    private var tvDate: TextView
    private var tvAilgnments: TextView

    init {
        inflate(context, R.layout.benefit_view, this)

        tvName = tv_item_disease_name
        tvDate = tv_item_days
        tvAilgnments = tv_item_ailments
    }

    fun setCap(name: String, date : String, ailments : String)
    {
        tvName.text = name
        tvDate.text = date
        tvAilgnments.text = ailments
    }
}