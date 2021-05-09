package com.rafalbiarda.medcinedontforgetversion8.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.andrognito.pinlockview.IndicatorDots
import com.andrognito.pinlockview.PinLockListener
import com.andrognito.pinlockview.PinLockView
import com.rafalbiarda.medcinedontforgetversion8.R
import com.rafalbiarda.medcinedontforgetversion8.firebase.FirestoreClass
import com.rafalbiarda.medcinedontforgetversion8.ui.activity.MainActivity
import kotlinx.android.synthetic.main.fragment_pin_lock.*


class PinLockFragment : BaseFragment() {

    var mPinLockView: PinLockView? = null
    var mIndicatorDots: IndicatorDots? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pin_lock, container, false)
    }

    private val mPinLockListener: PinLockListener = object : PinLockListener {
        override fun onComplete(pin: String) {
            showLog("Pin complete: $pin")
            val userPin = FirestoreClass().getUserPin().toString()
            if(pin == userPin)
            {
                val action = MedicineFragmentDirections.actionGlobalMedicineFragment()
                findNavController().navigate(action)
            }
        }

        override fun onEmpty() {
            showLog("Pin empty")
        }

        override fun onPinChange(pinLength: Int, intermediatePin: String) {
            showLog("Pin changed, new length $pinLength with intermediate pin $intermediatePin")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mPinLockView = pin_lock_view
        mPinLockView!!.setPinLockListener(mPinLockListener)
        mIndicatorDots = indicator_dots
        mPinLockView!!.attachIndicatorDots(mIndicatorDots)
    }



}