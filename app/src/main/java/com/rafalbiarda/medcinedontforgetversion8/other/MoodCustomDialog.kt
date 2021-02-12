package com.rafalbiarda.medcinedontforgetversion8.other

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatDialog
import com.hsalf.smileyrating.SmileyRating
import com.rafalbiarda.medcinedontforgetversion8.R
import kotlinx.android.synthetic.main.dialog_mood_raiting.*

class MoodCustomDialog(context: Context, var moodCustomDialogListener: MoodCustomDialogListener) :
    AppCompatDialog(context) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var rating = 0
        setContentView(R.layout.dialog_mood_raiting)

        smile_raiting.setSmileySelectedListener {

            when (it) {
                SmileyRating.Type.BAD -> rating = 2
                SmileyRating.Type.GOOD -> rating = 4
                SmileyRating.Type.GREAT -> rating = 5
                SmileyRating.Type.NONE -> rating = 0
                SmileyRating.Type.OKAY -> rating = 3
                SmileyRating.Type.TERRIBLE -> rating = 1
            }

        }

        btn_mood_dialog_done.setOnClickListener {
            moodCustomDialogListener.onAddButtonClicked(rating)
            dismiss()
        }


    }

}