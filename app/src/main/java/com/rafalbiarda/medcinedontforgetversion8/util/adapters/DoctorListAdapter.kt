package com.rafalbiarda.medcinedontforgetversion8.util.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rafalbiarda.medcinedontforgetversion8.R
import com.rafalbiarda.medcinedontforgetversion8.model.Doctor
import com.rafalbiarda.medcinedontforgetversion8.ui.fragments.DoctorsListFragmentDirections
import com.rafalbiarda.medcinedontforgetversion8.util.MyDiffUtil
import kotlinx.android.synthetic.main.item_doctor_list.view.*


class DoctorListAdapter() :
    RecyclerView.Adapter<DoctorListAdapter.DoctorListViewHolder>() {

    inner class DoctorListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private var list = emptyList<Doctor>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorListViewHolder {

        return DoctorListViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_doctor_list,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DoctorListViewHolder, position: Int) {

        val fullName = list[position].firstName + " " + list[position].lastName
        holder.itemView.tv_doctor_name.text = fullName
        holder.itemView.setOnClickListener {
            val action = DoctorsListFragmentDirections.actionDoctorsFragmentToDoctorDetailFragment(list[position])
            it.findNavController().navigate(action)
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }


    fun setData(newData: List<Doctor>)
    {
        val myDiffUtil = MyDiffUtil(list, newData)
        val diffUtilResult = DiffUtil.calculateDiff(myDiffUtil)
        list = newData
        diffUtilResult.dispatchUpdatesTo(this)
    }
}