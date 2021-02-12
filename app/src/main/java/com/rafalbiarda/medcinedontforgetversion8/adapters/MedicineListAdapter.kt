package com.rafalbiarda.medcinedontforgetversion8.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rafalbiarda.medcinedontforgetversion8.R
import com.rafalbiarda.medcinedontforgetversion8.models.MeasurementReminder
import com.rafalbiarda.medcinedontforgetversion8.models.Medicine
import com.rafalbiarda.medcinedontforgetversion8.models.MedicineReminder
import com.rafalbiarda.medcinedontforgetversion8.ui.fragments.MedicineListFragmentDirections
import com.rafalbiarda.medcinedontforgetversion8.util.MyDiffUtil
import kotlinx.android.synthetic.main.item_medicine_list.view.*
import kotlinx.android.synthetic.main.item_reminder_adapter.view.tv_name
import kotlinx.android.synthetic.main.item_reminder_adapter2.view.*
import java.text.SimpleDateFormat
import java.util.*

class MedicineListAdapter() :
    RecyclerView.Adapter<MedicineListAdapter.MedicineListViewHolder>() {

    inner class MedicineListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private var list = emptyList<Medicine>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicineListViewHolder {

        return MedicineListViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_medicine_list,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MedicineListViewHolder, position: Int) {

        holder.itemView.tv_medicine_name.text = list[position].name
        holder.itemView.setOnClickListener {
            val action = MedicineListFragmentDirections.actionMedsFragmentToMedicineDetailFragment(list[position])
            it.findNavController().navigate(action)
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }


    fun setData(newData: List<Medicine>)
    {
        val myDiffUtil = MyDiffUtil(list, newData)
        val diffUtilResult = DiffUtil.calculateDiff(myDiffUtil)
        list = newData
        diffUtilResult.dispatchUpdatesTo(this)
    }
}