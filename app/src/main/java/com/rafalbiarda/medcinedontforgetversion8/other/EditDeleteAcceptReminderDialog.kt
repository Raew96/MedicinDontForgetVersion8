package com.rafalbiarda.medcinedontforgetversion8.other

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatDialog
import com.rafalbiarda.medcinedontforgetversion8.R
import com.rafalbiarda.medcinedontforgetversion8.firebase.FirestoreClass
import com.rafalbiarda.medcinedontforgetversion8.model.Card
import com.rafalbiarda.medcinedontforgetversion8.model.MedicineReminder
import kotlinx.android.synthetic.main.dialog_edit_delete_accept_reminder.*

class EditDeleteAcceptReminderDialog(val card: Card, context: Context, val med : MedicineReminder, var editDeleteAcceptReminderDialogListener:  EditDeleteAcceptReminderDialogListener) :
    AppCompatDialog(context)
{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.dialog_edit_delete_accept_reminder)
        tv_medicine_name.text = med.medicine.name
        tv_dose.text = med.medicine.amount.toString() + " " + med.medicine.doseType
        tv_instructions.text = med.medicine.medicine_instructions

        btn_delete.setOnClickListener {
            FirestoreClass().deleteMedicineReminder(card, med)
            dismiss()
        }

        btn_take.setOnClickListener {
            FirestoreClass().takeMedicineReminder(card,med)
            dismiss()
        }
        

    }



}