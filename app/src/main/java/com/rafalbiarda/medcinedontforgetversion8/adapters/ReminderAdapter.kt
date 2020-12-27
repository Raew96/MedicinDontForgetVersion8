package com.rafalbiarda.medcinedontforgetversion8.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rafalbiarda.medcinedontforgetversion8.R
import com.rafalbiarda.medcinedontforgetversion8.models.MeasurementReminder
import com.rafalbiarda.medcinedontforgetversion8.models.MedicineReminder
import com.rafalbiarda.medcinedontforgetversion8.models.Reminder
import kotlinx.android.synthetic.main.item_reminder_adapter.view.*
import kotlinx.android.synthetic.main.item_reminder_adapter.view.tv_hour_reminder_adapter
import kotlinx.android.synthetic.main.item_reminder_adapter.view.tv_name
import kotlinx.android.synthetic.main.item_reminder_adapter2.view.*
import java.text.SimpleDateFormat
import java.util.*

class ReminderAdapter(var list: List<Reminder>) : RecyclerView.Adapter<ReminderAdapter.ReminderAdapterViewHolder>() {

    inner class  ReminderAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReminderAdapterViewHolder {

        when(viewType)
        {
            0 ->
            {
                return ReminderAdapterViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.item_reminder_adapter,
                        parent,
                        false
                    )
                )
            }
            1 ->
            {
                return ReminderAdapterViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.item_reminder_adapter2,
                        parent,
                        false
                    )
                )
            }
        }


        return ReminderAdapterViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_reminder_adapter,
                parent,
                false
            )
        )
    }

    override fun getItemViewType(position: Int): Int {

        val compare = list[position]

        when(compare)
        {
            is MedicineReminder -> return 0
            is MeasurementReminder ->return 1
        }

        return super.getItemViewType(position)
    }

    override fun onBindViewHolder(holder: ReminderAdapterViewHolder, position: Int) {

        if(list[position] is MedicineReminder) {
            holder.itemView.tv_name.text = (list[position] as MedicineReminder).medicine.name
            holder.itemView.tv_dose.text =
                (list[position] as MedicineReminder).medicine.dose.toString()
            holder.itemView.tv_dose_unit.text =
                (list[position] as MedicineReminder).medicine.doseType
            val time = (list[position] as MedicineReminder).timestamp
            val formatter = SimpleDateFormat("HH:mm")
            holder.itemView.tv_hour_reminder_adapter.text = formatter.format(Date(time))
        }
        else if(list[position] is MeasurementReminder) {
            holder.itemView.tv_name.text = (list[position] as MeasurementReminder).measurement.name
            val time = (list[position] as MeasurementReminder).timestamp
            val formatter = SimpleDateFormat("HH:mm")
            holder.itemView.tv_hour_redminder_adapter2.text = formatter.format(Date(time))
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}