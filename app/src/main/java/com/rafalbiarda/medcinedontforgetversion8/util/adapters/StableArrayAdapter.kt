package com.rafalbiarda.medcinedontforgetversion8.util.adapters

import android.content.Context
import android.widget.ArrayAdapter


class StableArrayAdapter(
    context: Context, textViewResourceId: Int,
    objects: List<String>
) : ArrayAdapter<String>(context, textViewResourceId, objects) {

    var mIdMap = HashMap<String, Int>()

    override fun getItemId(position: Int): Long {
        val item = getItem(position)
        return mIdMap.get(item)?.toLong()!!
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    init {
        for (i in objects.indices) {
            mIdMap[objects[i]] = i
        }
    }
}