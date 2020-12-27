package com.rafalbiarda.medcinedontforgetversion8.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.rafalbiarda.medcinedontforgetversion8.R
import com.rafalbiarda.medcinedontforgetversion8.adapters.ReminderAdapter
import com.rafalbiarda.medcinedontforgetversion8.models.*
import kotlinx.android.synthetic.main.fragment_medicine.*


class MedicineFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_medicine, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val med1 = Medicine(null, "Aspirine", "Apirintor", "Take always if you want to remove pain in the head", 60, 2, "tables")
        val med2 = Medicine(null, "Medikament", "Apirintor", "Take always before eat", 33, 1, "tables")
        val med3 = Medicine(null, "Ibuprom", "DoctorMax", "Take to remove pain in the head", 10, 2, "tables")
        val med4 = Medicine(null, "Liq", "Mentor", "Take a", 500, 20, "mililitr")
        val med5 = Medicine(null, "Aspi", "Apirintor", "Takeove pain in the head", 12, 2, "tables")

        val mea1 = Measurement("HeartPressure")
        val mea2 = Measurement("Sugar")


        val list = mutableListOf<Reminder>()
        list.add(MedicineReminder(System.currentTimeMillis()+100000,med1))
        list.add(MedicineReminder(System.currentTimeMillis()+200000,med2))
        list.add(MeasurementReminder(System.currentTimeMillis()+300000, mea1))
        list.add(MeasurementReminder(System.currentTimeMillis()+400000, mea2))
        list.add(MedicineReminder(System.currentTimeMillis()+500000,med3))
        list.add(MedicineReminder(System.currentTimeMillis()+600000,med4))
        list.add(MedicineReminder(System.currentTimeMillis()+700000,med5))

        val adapter = ReminderAdapter(list)

        recyclerView.layoutManager= LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }

}