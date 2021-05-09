package com.rafalbiarda.medcinedontforgetversion8.util.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rafalbiarda.medcinedontforgetversion8.R
import com.rafalbiarda.medcinedontforgetversion8.model.Disease
import com.rafalbiarda.medcinedontforgetversion8.other.AddEditDiseaseDialog
import com.rafalbiarda.medcinedontforgetversion8.other.AddEditDiseaseDialogListener
import com.rafalbiarda.medcinedontforgetversion8.util.MyDiffUtil
import kotlinx.android.synthetic.main.item_disease.view.*


class DiseaseAdapter(var context: Context) :

    RecyclerView.Adapter<DiseaseAdapter.DiseaseAdapterViewHolder>() {
    inner class DiseaseAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private var list =  emptyList<Disease>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiseaseAdapterViewHolder {

        return DiseaseAdapterViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_disease, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: DiseaseAdapterViewHolder, position: Int) {
        holder.itemView.tv_disease_name.text = list[position].name
        var strSymptomsList = list[position].symptoms.split(",").toMutableList()
        strSymptomsList.forEachIndexed { index, s ->
            strSymptomsList.set(index, "- $s")
        }
        var formateSymptomsList = strSymptomsList.joinToString (separator = "\n")

        holder.itemView.tv_list_of_symptoms.text = formateSymptomsList

        holder.itemView.iv_more_options.setOnClickListener {

            val popup = PopupMenu(context, holder.itemView.iv_more_options)
            popup.inflate(R.menu.disease_item_menu)

            popup.setOnMenuItemClickListener {

                when(it.itemId)
                {
                    R.id.edit -> {
                        AddEditDiseaseDialog(context, object : AddEditDiseaseDialogListener{

                            override fun onCallBack(disease: String, ailments : String) {

                                holder.itemView.tv_disease_name.text = disease;
                                holder.itemView.tv_list_of_symptoms.text = ailments;

                            }

                        }).show()
                        true
                    }
                    R.id.delete -> {
                        Log.e("MenuTest", "Delete")
                        true
                    }
                    else ->
                    {
                        false
                    }
                }
            }
            popup.show()
        }
    }

    override fun getItemCount(): Int {

        return list.size

    }

    fun setData(newData: List<Disease>)
    {
        val myDiffUtil = MyDiffUtil(list, newData)
        val diffUtilResult = DiffUtil.calculateDiff(myDiffUtil)
        list = newData
        diffUtilResult.dispatchUpdatesTo(this)
    }
}