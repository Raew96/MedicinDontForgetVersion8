package com.rafalbiarda.medcinedontforgetversion8.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hsalf.smileyrating.SmileyRating
import com.rafalbiarda.medcinedontforgetversion8.R
import com.rafalbiarda.medcinedontforgetversion8.model.DiseaseWithDate
import com.rafalbiarda.medcinedontforgetversion8.model.MedicineWithHistory
import com.rafalbiarda.medcinedontforgetversion8.model.WeeklyStatisticsCard
import com.rafalbiarda.medcinedontforgetversion8.util.BenefitView
import com.rafalbiarda.medcinedontforgetversion8.util.BenefitView2
import com.rafalbiarda.medcinedontforgetversion8.util.StraightLine
import kotlinx.android.synthetic.main.fragment_weekly_statistics.*
import java.text.SimpleDateFormat
import java.util.*


class WeeklyStatisticsFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weekly_statistics, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val listview = disease_linearlayout
        val listview2 = medicines_linearlayout

        val sdf = SimpleDateFormat("dd/M/yyyy")
        val currentDate = sdf.format(Date())

        val diseasesFromBase = WeeklyStatisticsCard(
            id = "qwertyuiop",
            weekOfYear = 18,
            ratingsOfMood =  listOf(1,2,3,4,5,5,5,5,4,3,2,5,4,5,5,3,4),
            ratingsOfSleep = listOf(1,2,3,4,5,1,2,1,1,1,1,2,1,1,1,1,1),
            diseasesWithDate = listOf(
                DiseaseWithDate("Covid", "cough, fever, headache", setAndGetDate(2021,5,7)),
                DiseaseWithDate("Covid", "cough, fever",setAndGetDate(2021,5,6)),
                DiseaseWithDate("Covid", "cough", setAndGetDate(2021,5,5))
            ),
            medicineWithHistory = listOf(
                MedicineWithHistory( "Apap", listOf(
                    setAndGetDate(2021,5,4,8,15), setAndGetDate(2021,5,5,8,15), setAndGetDate(2021,5,6, 8, 15))),
                MedicineWithHistory( "Paracetamol", listOf(setAndGetDate(2021,5,1, 18,30), setAndGetDate(2021,5,5, 18,30))),
                MedicineWithHistory( "Alegra", listOf( setAndGetDate(2021,5,4, 12)))
            )
        )

        tv_avg_mood_value.text = when(diseasesFromBase.ratingsOfMood.average().toInt())
        {
            1 -> "TERRIBLE"
            2 -> "BAD"
            3 -> "OKAY"
            4 -> "GOOD"
            5 -> "GREAT"
            else -> "NONE"
        }

        tv_avg_sleep_value.text = when(diseasesFromBase.ratingsOfSleep.average().toInt())
        {
            1 -> "TERRIBLE"
            2 -> "BAD"
            3 -> "OKAY"
            4 -> "GOOD"
            5 -> "GREAT"
            else -> "NONE"
        }

        diseasesFromBase.diseasesWithDate.forEach {
            val customView = BenefitView(requireContext())
            val straightLine = StraightLine(requireContext())
            customView.setCap(it.name, sdf.format(it.date), it.symptoms)
            listview.addView(customView)
            listview.addView(straightLine)
        }

        diseasesFromBase.medicineWithHistory.forEach {
            val customView2 = BenefitView2(requireContext())
            val straightLine2 = StraightLine(requireContext())
            customView2.setCap(it.name, it.history.joinToString { sdf.format(it) })
            listview2.addView(customView2)
            listview2.addView(straightLine2)
        }

    }

    fun setAndGetDate(year:Int, month:Int, day:Int, hour:Int = 0, minute:Int = 0, second:Int = 0 ) : Date
    {
        val calendar = Calendar.getInstance()
        calendar.set(year,month,day,hour,minute,second)

        return calendar.time
    }
}