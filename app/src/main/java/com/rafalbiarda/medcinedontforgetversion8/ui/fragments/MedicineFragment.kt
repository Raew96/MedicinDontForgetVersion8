package com.rafalbiarda.medcinedontforgetversion8.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.rafalbiarda.medcinedontforgetversion8.R
import com.rafalbiarda.medcinedontforgetversion8.util.adapters.ReminderAdapter
import com.rafalbiarda.medcinedontforgetversion8.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_medicine.*


class MedicineFragment : Fragment() {

    lateinit var viewModel: MainViewModel

    private val mAdapter by lazy { ReminderAdapter(requireContext(), this) }

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

        viewModel.actualCard.observe(viewLifecycleOwner, { thiss->
            mAdapter.setData(thiss)
        })

        viewModel.userCards.observe(viewLifecycleOwner, {
           activity?.main_single_row_calendar?.select(0)
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