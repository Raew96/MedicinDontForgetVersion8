package com.rafalbiarda.medcinedontforgetversion8.ui.fragments

import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.rafalbiarda.medcinedontforgetversion8.R
import com.rafalbiarda.medcinedontforgetversion8.adapters.DiseaseAdapter
import com.rafalbiarda.medcinedontforgetversion8.adapters.MoodAdapter
import com.rafalbiarda.medcinedontforgetversion8.models.Disease
import com.rafalbiarda.medcinedontforgetversion8.models.Mood
import com.rafalbiarda.medcinedontforgetversion8.other.MoodCustomDialog
import com.rafalbiarda.medcinedontforgetversion8.other.MoodCustomDialogListener
import kotlinx.android.synthetic.main.fragment_health.*
import java.util.*


class HealthFragment : Fragment() {

    private val mMoodAdapter by lazy { MoodAdapter() }
    private val mDiseaseAdapter by lazy { DiseaseAdapter(requireContext()) }
    private var moodList = mutableListOf<Mood>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_health, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var tempList = mutableListOf<Disease>()

        tempList.add(Disease("Covid", listOf("Cough", "faver", "sth")))
        tempList.add(Disease("Covid", listOf("Cough", "faver", "sth")))
        tempList.add(Disease("Covid", listOf("Cough", "faver", "sth")))

        mDiseaseAdapter.setData(tempList)

        setMoodAdapter()
        setDisease()

        btn_pick_mood.setOnClickListener {
            MoodCustomDialog(requireContext(), object  : MoodCustomDialogListener
            {
                override fun onAddButtonClicked(ratings: Int) {

                    val calendar = Calendar.getInstance()
                    val hour = calendar.get(Calendar.HOUR_OF_DAY)
                    val minutes = calendar.get(Calendar.MINUTE)
                    val mood = Mood("$hour:$minutes", ratings)
                    moodList.add(mood)
                    mMoodAdapter.setData(moodList)
                    setMoodAdapter()
                }

            }).show()
        }


        btn_sleep.setOnClickListener {

            MoodCustomDialog(requireContext(), object  : MoodCustomDialogListener
            {
                override fun onAddButtonClicked(ratings: Int) {

                    when(ratings)
                    {
                        0 -> tv_sleep_quality.text = "Not picked"
                        1 -> tv_sleep_quality.text = "Terrible"
                        2 -> tv_sleep_quality.text = "Bad"
                        3 -> tv_sleep_quality.text = "Okay"
                        4 -> tv_sleep_quality.text = "Good"
                        5 -> tv_sleep_quality.text = "Great"
                    }
                    tv_sleep_quality.visibility = View.VISIBLE
                }

            }).show()

        }

        iv_mood_arrow.setOnClickListener {
            if(hiden_layout.visibility == View.GONE)
            {
                TransitionManager.beginDelayedTransition(cardViewMood, AutoTransition())
                hiden_layout.visibility = View.VISIBLE
                iv_mood_arrow.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp)
            }
            else
            {
                hiden_layout.visibility = View.GONE
                iv_mood_arrow.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp)
            }
        }


        iv_sleep_arrow.setOnClickListener {
            if(hiden_layout_sleep.visibility == View.GONE)
            {
                TransitionManager.beginDelayedTransition(cv_sleep, AutoTransition())
                hiden_layout_sleep.visibility = View.VISIBLE
                iv_sleep_arrow.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp)
            }
            else
            {
                hiden_layout_sleep.visibility = View.GONE
                iv_sleep_arrow.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp)
            }
        }

        iv_disease_arrow.setOnClickListener {
            if(hiden_layout_disease.visibility == View.GONE)
            {
                TransitionManager.beginDelayedTransition(cv_disease, AutoTransition())
                hiden_layout_disease.visibility = View.VISIBLE
                iv_disease_arrow.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp)
            }
            else
            {
                hiden_layout_disease.visibility = View.GONE
                iv_disease_arrow.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp)
            }
        }
    }

    fun setMoodAdapter()
    {
        rv_mood.adapter = mMoodAdapter
        rv_mood.layoutManager = LinearLayoutManager(context)
    }

    fun setDisease()
    {
        rv_disease.adapter = mDiseaseAdapter
        rv_disease.layoutManager = LinearLayoutManager(context)
    }

}