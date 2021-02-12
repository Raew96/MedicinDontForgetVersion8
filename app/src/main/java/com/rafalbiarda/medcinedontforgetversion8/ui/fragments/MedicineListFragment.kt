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
import com.rafalbiarda.medcinedontforgetversion8.adapters.MedicineListAdapter
import com.rafalbiarda.medcinedontforgetversion8.adapters.ReminderAdapter
import com.rafalbiarda.medcinedontforgetversion8.models.Medicine
import com.rafalbiarda.medcinedontforgetversion8.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.fragment_medicine_list.*


class MedicineListFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private val mAdapter by lazy { MedicineListAdapter() }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_medicine_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        setRecyclerViewAdapter()


        viewModel.userMedicineList.observe(viewLifecycleOwner, {thiss->
            mAdapter.setData(thiss)
        })

    }

    fun setRecyclerViewAdapter()
    {
        recyclerView.adapter = mAdapter
        recyclerView.layoutManager= LinearLayoutManager(requireContext())
    }

}