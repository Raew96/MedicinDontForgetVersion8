package com.rafalbiarda.medcinedontforgetversion8.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rafalbiarda.medcinedontforgetversion8.R
import com.rafalbiarda.medcinedontforgetversion8.other.MyCustomDialog
import com.rafalbiarda.medcinedontforgetversion8.other.MyCustomDialogListener
import kotlinx.android.synthetic.main.item_add_medicne_hour.view.*

class HourItemAdapter(var list : ArrayList<String>, var context: Context) : RecyclerView.Adapter<HourItemAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {

        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_add_medicne_hour, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {


        holder.itemView.setOnClickListener {
            MyCustomDialog(context, object : MyCustomDialogListener
            {
                override fun onAddButtonClicked(hour: Int, minute: Int, dose: Int) {
                    holder.itemView.tv_dose_changeable.text = dose.toString()
                    holder.itemView.tv_hour_changeable.text = "$hour:$minute"
                }
            }).show()
        }
        return
    }


}