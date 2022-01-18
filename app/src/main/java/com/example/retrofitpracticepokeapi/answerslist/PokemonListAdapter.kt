package com.example.retrofitpracticepokeapi.answerslist

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class PokemonListAdapter(context: Context) : BaseAdapter() {

    private val mContext: Context

    init {
        mContext = context
    }
    override fun getCount(): Int {
        return 151
    }

    override fun getItem(p0: Int): Any {
        return "TEST STRING"
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        return TextView(mContext)
    }

}
