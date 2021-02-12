package com.rafalbiarda.medcinedontforgetversion8.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.rafalbiarda.medcinedontforgetversion8.R
import com.rafalbiarda.medcinedontforgetversion8.adapters.ReminderAdapter
import com.rafalbiarda.medcinedontforgetversion8.models.*
import com.rafalbiarda.medcinedontforgetversion8.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.fragment_add_medicine.*
import kotlinx.android.synthetic.main.fragment_medicine.*
import java.util.*


class MedicineFragment : Fragment() {

    lateinit var viewModel: MainViewModel

    private val mAdapter by lazy { ReminderAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_medicine, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        setRecyclerViewAdapter()
        /*val med = Medicine(null, "Cetonal", "Aspirix", "Take always before eat", 60, 2, "tables")

        val list = mutableListOf<MedicineReminder>()
        list.add(MedicineReminder(Calendar.getInstance().time, "1", med))
        list.add(MedicineReminder(Calendar.getInstance().time, "2", med))
        list.add(MedicineReminder(Calendar.getInstance().time, "3", med))

        val adapter = ReminderAdapter(list)

        recyclerView.layoutManager= LinearLayoutManager(context)
        recyclerView.adapter = adapter*/

        viewModel.redmindersMeds.observe(viewLifecycleOwner, { thiss->
            mAdapter.setData(thiss)
        })


        fab.setOnClickListener {
            findNavController().navigate(R.id.action_medicineFragment_to_addMedicineFragment)
        }

    }

    fun setRecyclerViewAdapter()
    {
        recyclerView.adapter = mAdapter
        recyclerView.layoutManager= LinearLayoutManager(requireContext())
    }

}