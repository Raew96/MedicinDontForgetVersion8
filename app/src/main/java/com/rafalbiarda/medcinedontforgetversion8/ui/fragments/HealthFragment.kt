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
import com.rafalbiarda.medcinedontforgetversion8.adapters.MoodAdapter
import com.rafalbiarda.medcinedontforgetversion8.models.Mood
import kotlinx.android.synthetic.main.fragment_health.*


class HealthFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_health, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val mood1 = Mood("8:00", 8)
        val mood2 = Mood("12:00", 1)
        val mood3 = Mood("18:00", 10)
        val adapter = MoodAdapter(listOf(mood1,mood2,mood3))
        rv_mood.layoutManager = LinearLayoutManager(context)
        rv_mood.adapter = adapter

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

}