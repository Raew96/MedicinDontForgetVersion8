package com.rafalbiarda.medcinedontforgetversion8.ui.fragments

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.rafalbiarda.medcinedontforgetversion8.R
import com.rafalbiarda.medcinedontforgetversion8.ui.activity.MainActivity
import com.rafalbiarda.medcinedontforgetversion8.util.ReminderBroadcast
import com.rafalbiarda.medcinedontforgetversion8.util.adapters.PagerAdapter
import com.rafalbiarda.medcinedontforgetversion8.util.adapters.StableArrayAdapter
import kotlinx.android.synthetic.main.fragment_statistics.*


class StatisticsFragment : Fragment() {

    lateinit var fragmentWeekly : WeeklyStatisticsFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment



        return inflater.inflate(R.layout.fragment_statistics, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onResume() {
        super.onResume()

        (activity as MainActivity).setToolbar("Statistics")
        
        val fragments = ArrayList<Fragment>()
        fragmentWeekly = WeeklyStatisticsFragment()
        fragments.add(fragmentWeekly)
        fragments.add(MonthlyStatisticsFragment())

        val titles = ArrayList<String>()
        titles.add("Weekly")
        titles.add("Monthly")

        val resultBundle = Bundle()


        val adapter = PagerAdapter(
            resultBundle,
            fragments,
            titles,
            activity?.supportFragmentManager!!
        )

        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)

    }




    private fun addNotificationReminder()
    {
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, ReminderBroadcast::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        alarmManager.set(AlarmManager.RTC_WAKEUP, 5000, pendingIntent)
    }




}