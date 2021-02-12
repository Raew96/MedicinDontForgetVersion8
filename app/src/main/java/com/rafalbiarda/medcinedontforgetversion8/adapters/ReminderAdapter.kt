package com.rafalbiarda.medcinedontforgetversion8.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rafalbiarda.medcinedontforgetversion8.R
import com.rafalbiarda.medcinedontforgetversion8.models.MeasurementReminder
import com.rafalbiarda.medcinedontforgetversion8.models.MedicineReminder
import com.rafalbiarda.medcinedontforgetversion8.util.MyDiffUtil
import kotlinx.android.synthetic.main.item_reminder_adapter.view.tv_name
import kotlinx.android.synthetic.main.item_reminder_adapter2.view.*
import java.text.SimpleDateFormat
import java.util.*

class ReminderAdapter() :
    RecyclerView.Adapter<ReminderAdapter.ReminderAdapterViewHolder>() {

    inner class ReminderAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private var list = emptyList<MedicineReminder>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReminderAdapterViewHolder {

        return ReminderAdapterViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_reminder_adapter,
                parent,
                false
            )
        )

    }


    override fun onBindViewHolder(holder: ReminderAdapterViewHolder, position: Int) {


        holder.itemView.tv_name.text = list[position].medicine.name

    }

    override fun getItemCount(): Int {
        return list.size
    }


    fun setData(newData: List<MedicineReminder>)
    {
        val myDiffUtil = MyDiffUtil(list, newData)
        val diffUtilResult = DiffUtil.calculateDiff(myDiffUtil)
        list = newData
        diffUtilResult.dispatchUpdatesTo(this)
    }
}