package com.rafalbiarda.medcinedontforgetversion8.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rafalbiarda.medcinedontforgetversion8.R
import com.rafalbiarda.medcinedontforgetversion8.models.MedicineReminder
import com.rafalbiarda.medcinedontforgetversion8.models.Mood
import com.rafalbiarda.medcinedontforgetversion8.util.MyDiffUtil
import kotlinx.android.synthetic.main.item_healt_mood.view.*

class MoodAdapter() :
    RecyclerView.Adapter<MoodAdapter.MoodAdapterViewHolder>() {
    inner class MoodAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private var list =  emptyList<Mood>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoodAdapterViewHolder {

        return MoodAdapterViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_healt_mood, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MoodAdapterViewHolder, position: Int) {
        holder.itemView.tv_hour_mood.text = list[position].time
        holder.itemView.tv_item_mood.text = list[position].value.toString()
    }

    override fun getItemCount(): Int {

        return list.size

    }

    fun setData(newData: List<Mood>)
    {
        val myDiffUtil = MyDiffUtil(list, newData)
        val diffUtilResult = DiffUtil.calculateDiff(myDiffUtil)
        list = newData
        diffUtilResult.dispatchUpdatesTo(this)
    }
}