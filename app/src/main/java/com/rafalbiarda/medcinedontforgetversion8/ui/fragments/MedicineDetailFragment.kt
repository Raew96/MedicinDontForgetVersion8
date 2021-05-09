package com.rafalbiarda.medcinedontforgetversion8.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.rafalbiarda.medcinedontforgetversion8.R
import kotlinx.android.synthetic.main.fragment_medcine_detail.*


class MedicineDetailFragment : Fragment() {

    val args: MedicineDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_medcine_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val medicine = args.medicine

        tv_medicine_name.text = medicine.name
        tv_producer_name.text = medicine.producerName
        tv_instructions.text = medicine.medicine_instructions
        tv_amount.text = medicine.amount.toString()
        tv_dose_type.text = medicine.doseType
    }

}