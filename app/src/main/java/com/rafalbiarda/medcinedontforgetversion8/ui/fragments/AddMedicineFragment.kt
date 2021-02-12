package com.rafalbiarda.medcinedontforgetversion8.ui.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.TextUtils
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rafalbiarda.medcinedontforgetversion8.R
import com.rafalbiarda.medcinedontforgetversion8.adapters.HourItemAdapter
import com.rafalbiarda.medcinedontforgetversion8.firestore.FirestoreClass
import com.rafalbiarda.medcinedontforgetversion8.models.Card
import com.rafalbiarda.medcinedontforgetversion8.models.Medicine
import com.rafalbiarda.medcinedontforgetversion8.models.MedicineReminder
import com.rafalbiarda.medcinedontforgetversion8.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.fragment_add_medicine.*
import kotlinx.android.synthetic.main.fragment_add_medicine.view.*
import kotlinx.android.synthetic.main.fragment_medicine.*
import kotlinx.android.synthetic.main.item_add_medicne_hour.view.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList


class AddMedicineFragment : BaseFragment() {

    lateinit var viewModel: MainViewModel

    lateinit var listOfCards: List<Card>

    lateinit var mAdapter: HourItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_medicine, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        viewModel.getUserCards().observe(viewLifecycleOwner, { cards ->
            listOfCards = cards
        })

        val calendar = Calendar.getInstance()
        var y = calendar.get(Calendar.YEAR)
        var m = calendar.get(Calendar.MONTH)
        var d = calendar.get(Calendar.DAY_OF_MONTH)

        val setListenerStart = DatePickerDialog.OnDateSetListener { datePicker, i, i2, i3 ->
            val date = "$i3/${i2+1}/$i"
            btn_start_date.text = date
        }

        val setListenerStop = DatePickerDialog.OnDateSetListener { datePicker, i, i2, i3 ->
            val date = "$i3/${i2+1}/$i"
            btn_stop_date.text = date
        }

        btn_times_a_day.setOnClickListener {

            val list = ArrayList<String>()

            for(i in 1..til_times_a_day.et_times_a_day.text.toString().toInt())
            {
                list.add(i.toString())
            }

            mAdapter = HourItemAdapter(list, requireContext())

            rv_reminder.layoutManager = LinearLayoutManager(context)
            rv_reminder.adapter = mAdapter
        }


        btn_start_date.setOnClickListener {
            val datePickerDialog = DatePickerDialog(requireContext(),
                setListenerStart, y,m,d)

            datePickerDialog.datePicker.minDate = calendar.timeInMillis

            datePickerDialog.show()
        }

        btn_stop_date.setOnClickListener {
            val datePickerDialog = DatePickerDialog(requireContext(),
                setListenerStop, y,m,d)

            datePickerDialog.datePicker.minDate = calendar.timeInMillis

            //datePickerDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            datePickerDialog.show()
        }


        medicinFragmentArrowBtn.setOnClickListener {

            if(medicineExpandableView.visibility == View.GONE)
            {
                TransitionManager.beginDelayedTransition(cardView, AutoTransition())
                medicineExpandableView.visibility = View.VISIBLE
                medicinFragmentArrowBtn.setBackgroundResource(R.drawable.ic_keyboard_arrow_up_black_24dp)
            }
            else
            {
                TransitionManager.beginDelayedTransition(cardView, AutoTransition())
                medicineExpandableView.visibility = View.GONE
                medicinFragmentArrowBtn.setBackgroundResource(R.drawable.ic_keyboard_arrow_down_black_24dp)
            }
        }

        switch_reminder.setOnClickListener {

            if(switch_reminder.isChecked)
            {
                TransitionManager.beginDelayedTransition(cardView2, AutoTransition())
                reminderExpandableView.visibility = View.VISIBLE
            }
            else
            {
                TransitionManager.beginDelayedTransition(cardView2, AutoTransition())
                reminderExpandableView.visibility = View.GONE
            }
        }

        floatingActionButton.setOnClickListener {

            if(validateAddMedicine() && validateAddReminder())
            {
                showToast("Medicine Added and sheduler")

                val formatter = SimpleDateFormat("dd/m/yy", Locale.getDefault())
                val formatter2 = SimpleDateFormat("dd/m/yy HH:mm", Locale.getDefault())
                val date = formatter.parse(btn_start_date.text.toString())
                var listOfMedicineReminder = mutableListOf<MedicineReminder>()

                val medicineName = et_medicine_name.text.toString().trim() { it <= ' ' }
                val med = Medicine(name = medicineName)


                for(i in 0 until mAdapter.list.size) {
                    val holder = rv_reminder.findViewHolderForAdapterPosition(i)
                    val time = holder?.itemView?.tv_hour_changeable?.text.toString()
                    val timeAndDate = btn_start_date.text.toString() + " " + time
                    val medicineReminderDate = formatter2.parse(timeAndDate)
                    listOfMedicineReminder.add(MedicineReminder(date = medicineReminderDate, medicine = med))
                    Log.e("Test1", timeAndDate)
                }
                FirestoreClass().AddMedcineReminder(date, listOfCards, listOfMedicineReminder)
            }
            else if(validateAddMedicine())
            {
                val medicineName = et_medicine_name.text.toString().trim() { it <= ' ' }
                val medicineProducer = et_producer_name.text.toString().trim() { it <= ' ' }
                val medicineInstruction = et_instructions.text.toString().trim() { it <= ' ' }
                val medicineDoseType = searchableSpinner.selectedItem.toString()
                val medicineAmount = et_amount_of_medicine.text.toString().trim() { it <= ' ' }

                val med = Medicine(null, medicineName, medicineProducer, medicineInstruction,medicineAmount.toInt(), medicineDoseType )


                FirestoreClass().addMedicineToUserList(med)
            }
        }
    }

    private fun validateAddMedicine() : Boolean
    {
        return when{
            TextUtils.isEmpty(et_medicine_name.text.toString().trim { it <= ' ' }) -> {
                val message = resources.getString(R.string.err_msg_enter_medicine_name)
                showSnackBar(message)
                false
            }
            else -> {
                true
            }
        }
    }

    private fun validateAddReminder() : Boolean
    {
        if(switch_reminder.isChecked) {
            return when {
                btn_start_date.text.toString() == "Select Date" -> {
                    showSnackBar("Enter date of start")
                    false
                }
                TextUtils.isEmpty(et_times_a_day.text.toString().trim() { it <= ' ' }) -> {
                    showSnackBar("Enter times a day")
                    false
                }
                else -> {
                    true
                }
            }
        }
        else
        {
            return false
        }
    }




}