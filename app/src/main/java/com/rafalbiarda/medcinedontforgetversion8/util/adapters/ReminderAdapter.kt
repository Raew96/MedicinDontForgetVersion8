package com.rafalbiarda.medcinedontforgetversion8.util.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rafalbiarda.medcinedontforgetversion8.R
import com.rafalbiarda.medcinedontforgetversion8.model.Card
import com.rafalbiarda.medcinedontforgetversion8.model.Medicine
import com.rafalbiarda.medcinedontforgetversion8.model.MedicineReminder
import com.rafalbiarda.medcinedontforgetversion8.other.EditDeleteAcceptReminderDialog
import com.rafalbiarda.medcinedontforgetversion8.other.EditDeleteAcceptReminderDialogListener
import com.rafalbiarda.medcinedontforgetversion8.ui.fragments.MedicineFragment
import com.rafalbiarda.medcinedontforgetversion8.util.MyDiffUtil
import kotlinx.android.synthetic.main.item_reminder_adapter.view.tv_name
import kotlinx.android.synthetic.main.item_reminder_adapter_taken.view.*
import org.w3c.dom.Text
import java.text.SimpleDateFormat

class ReminderAdapter(var context: Context, val fragment: MedicineFragment) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val VIEW_TYPE_ONE = 1
        const val VIEW_TYPE_TWO = 2
    }

    private var list = emptyList<MedicineReminder>()
    private lateinit var card : Card

    inner class View1ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        fun bind(position: Int) {
            var medicine_name: TextView = itemView.findViewById(R.id.tv_name)
            var reminderTakeTime : TextView = itemView.findViewById(R.id.tv_hour_reminder_adapter)
            val doseUnit : TextView = itemView.findViewById(R.id.tv_dose_unit)
            medicine_name.text = list[position].medicine.name
            val sdf = SimpleDateFormat( "HH:mm" )

            reminderTakeTime.text = sdf.format(list[position].date)

            doseUnit.text = list[position].medicine.doseType


            itemView.setOnClickListener {
                val med = list[position]
                EditDeleteAcceptReminderDialog(card, context, med, object : EditDeleteAcceptReminderDialogListener{
                    override fun onCallBack(disease: String, ailments: String) {
                        fragment.setRecyclerViewAdapter()
                    }
                }).show()
            }

        }
    }

    inner class View2ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        fun bind(position: Int) {
            var medicine_name: TextView = itemView.findViewById(R.id.tv_name_taken)
            medicine_name.text = list[position].medicine.name

            itemView.setOnClickListener {
                val med = list[position]
                EditDeleteAcceptReminderDialog(card, context, med, object : EditDeleteAcceptReminderDialogListener{
                    override fun onCallBack(disease: String, ailments: String) {
                        fragment.setRecyclerViewAdapter()
                    }
                }).show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if(viewType == VIEW_TYPE_ONE)
        {
            return View1ViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_reminder_adapter, parent, false)
            )
        }

        return View2ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_reminder_adapter_taken, parent, false)
        )
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (!list[position].isTaken) {
            (holder as View1ViewHolder).bind(position)
        } else {
            (holder as View2ViewHolder).bind(position)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return if(list[position].isTaken)
            VIEW_TYPE_TWO
        else
            VIEW_TYPE_ONE
    }

    fun setData(newCard: Card)
    {
        val myDiffUtil = MyDiffUtil(list, newCard.medicineReminderList)
        val diffUtilResult = DiffUtil.calculateDiff(myDiffUtil)
        list = newCard.medicineReminderList
        card = newCard
        diffUtilResult.dispatchUpdatesTo(this)
    }
}

