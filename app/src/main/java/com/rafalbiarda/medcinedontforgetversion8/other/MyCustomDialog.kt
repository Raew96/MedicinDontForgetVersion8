package com.rafalbiarda.medcinedontforgetversion8.other

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialog
import androidx.fragment.app.DialogFragment
import com.rafalbiarda.medcinedontforgetversion8.R
import kotlinx.android.synthetic.main.dialog_time_and_dose.*
import java.lang.ClassCastException

class MyCustomDialog(context: Context, var myCustomDialog: MyCustomDialogListener) : AppCompatDialog(context) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_time_and_dose)

        timePicker.setIs24HourView(true)

        btn_cancel.setOnClickListener {
            cancel()
        }

        btn_ok.setOnClickListener {

            val h = timePicker.hour
            val m = timePicker.minute

            val dose = ed_dose_text.text.toString().toInt()

            myCustomDialog.onAddButtonClicked(h,m,dose)
            dismiss()
        }
    }



}