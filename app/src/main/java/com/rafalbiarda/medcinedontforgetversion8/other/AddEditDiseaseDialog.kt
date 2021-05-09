package com.rafalbiarda.medcinedontforgetversion8.other

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatDialog
import com.rafalbiarda.medcinedontforgetversion8.R
import kotlinx.android.synthetic.main.dialog_add_edit_disease.*

class AddEditDiseaseDialog(context: Context, var addEditDiseaseDialogListener: AddEditDiseaseDialogListener) :
    AppCompatDialog(context)
{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.dialog_add_edit_disease)

        btn_cancel.setOnClickListener {
            cancel()
        }

        btn_ok.setOnClickListener {

            val disease = et_disease.text.toString()
            val ail = et_ailments.text.toString()

            addEditDiseaseDialogListener.onCallBack(disease, ail)
            dismiss()
        }

    }



}