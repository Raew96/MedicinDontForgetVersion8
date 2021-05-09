package com.rafalbiarda.medcinedontforgetversion8.ui.fragments

import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rafalbiarda.medcinedontforgetversion8.R
import com.rafalbiarda.medcinedontforgetversion8.util.adapters.DiseaseAdapter
import com.rafalbiarda.medcinedontforgetversion8.util.adapters.MoodAdapter
import com.rafalbiarda.medcinedontforgetversion8.firebase.FirestoreClass
import com.rafalbiarda.medcinedontforgetversion8.model.Card
import com.rafalbiarda.medcinedontforgetversion8.model.Disease
import com.rafalbiarda.medcinedontforgetversion8.model.Mood
import com.rafalbiarda.medcinedontforgetversion8.other.AddEditDiseaseDialog
import com.rafalbiarda.medcinedontforgetversion8.other.AddEditDiseaseDialogListener
import com.rafalbiarda.medcinedontforgetversion8.other.MoodCustomDialog
import com.rafalbiarda.medcinedontforgetversion8.other.MoodCustomDialogListener
import com.rafalbiarda.medcinedontforgetversion8.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_health.*
import java.util.*


class HealthFragment : Fragment() {

    lateinit var viewModel : MainViewModel
    lateinit var cardListHealthFragment: List<Card>

    private val mMoodAdapter by lazy { MoodAdapter() }
    private val mDiseaseAdapter by lazy { DiseaseAdapter(requireContext()) }
    private var dateSetByUser = Date()
    private var actualCard = Card()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_health, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        setMoodAndDiseaseAdapter()

        viewModel.getUserCards().observe(viewLifecycleOwner, {thiss ->
            cardListHealthFragment = thiss

            cardListHealthFragment.forEach {

                if (dateSetByUser == it.date) {
                    mMoodAdapter.setData(it.health.mood)
                    mDiseaseAdapter.setData(it.health.diseases)
                    setMoodAndDiseaseAdapter()
                }
            }
        })

        viewModel.dateSetByUser.observe(viewLifecycleOwner, { thiss ->
            dateSetByUser = thiss

            var thisDayHaveACard = false
            cardListHealthFragment.forEach {

                if(dateSetByUser == it.date)
                {
                    thisDayHaveACard = true
                    actualCard = it
                    mMoodAdapter.setData(it.health.mood)
                    mDiseaseAdapter.setData(it.health.diseases)
                    tv_sleep_quality.text = it.health.sleep
                    tv_sleep_quality.visibility = View.VISIBLE
                    setMoodAndDiseaseAdapter()
                }
            }

            if(!thisDayHaveACard)
            {
                actualCard = Card()
                mMoodAdapter.setData(listOf())
                mDiseaseAdapter.setData(listOf())
                mMoodAdapter.notifyDataSetChanged()
                mDiseaseAdapter.notifyDataSetChanged()
                setMoodAndDiseaseAdapter()
                tv_sleep_quality.visibility = View.GONE
            }

        })


        
        btn_pick_mood.setOnClickListener {
            MoodCustomDialog(requireContext(), object  : MoodCustomDialogListener
            {
                override fun onAddButtonClicked(ratings: Int) {

                    val calendar = Calendar.getInstance()
                    val hour = calendar.get(Calendar.HOUR_OF_DAY)
                    val minutes = calendar.get(Calendar.MINUTE)
                    val mood = Mood("$hour:$minutes", ratings)
                    var mutableMoodList = mutableListOf<Mood>()
                    actualCard.date = dateSetByUser
                    actualCard.health.mood.forEach {
                        mutableMoodList.add(it)
                    }
                    mutableMoodList.add(mood)
                    actualCard.health.mood = mutableMoodList
                    FirestoreClass().addHealth(actualCard)
                }

            }).show()
        }


        btn_sleep.setOnClickListener {

            MoodCustomDialog(requireContext(), object  : MoodCustomDialogListener
            {
                override fun onAddButtonClicked(ratings: Int) {
                    var strRatings = ""
                    when(ratings)
                    {
                        0 -> strRatings = "Not picked"
                        1 -> strRatings = "Terrible"
                        2 -> strRatings = "Bad"
                        3 -> strRatings = "Okay"
                        4 -> strRatings = "Good"
                        5 -> strRatings = "Great"
                    }
                    tv_sleep_quality.text = strRatings
                    tv_sleep_quality.visibility = View.VISIBLE
                    actualCard.date = dateSetByUser
                    actualCard.health.sleep = strRatings
                    FirestoreClass().addHealth(actualCard)
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
        iv_add_disease.setOnClickListener {

            AddEditDiseaseDialog(requireContext(), object : AddEditDiseaseDialogListener {

                override fun onCallBack(disease: String, ailments : String) {

                    var mutableDiseaseList = mutableListOf<Disease>()
                    actualCard.date = dateSetByUser
                    actualCard.health.diseases.forEach {
                        mutableDiseaseList.add(it)
                    }
                    mutableDiseaseList.add(Disease(disease, ailments))
                    actualCard.health.diseases = mutableDiseaseList
                    FirestoreClass().addHealth(actualCard)
                }

            }).show()

        }
    }

    fun setMoodAndDiseaseAdapter()
    {
        rv_mood.adapter = mMoodAdapter
        rv_mood.layoutManager = LinearLayoutManager(context)
        rv_disease.adapter = mDiseaseAdapter
        rv_disease.layoutManager = LinearLayoutManager(context)
    }



}