package com.ganesh.hilt.mutualfund.sip.myportfolio.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.ganesh.hilt.mutualfund.sip.myportfolio.di.MFSchemeNameModel

class SchemeAdapter(context: Context, private var schemeList: List<MFSchemeNameModel>) :
    ArrayAdapter<MFSchemeNameModel>(
        context,
        android.R.layout.simple_dropdown_item_1line,
        schemeList
    ) {

    fun updateList(newList: List<MFSchemeNameModel>) {
        schemeList = newList
        notifyDataSetChanged() // Notify adapter to refresh
    }

    override fun getCount(): Int {
        return schemeList.size // Ensure count is updated
    }

    override fun getItem(position: Int): MFSchemeNameModel? {
        return schemeList[position]
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(android.R.layout.simple_dropdown_item_1line, parent, false)

        val textView: TextView = view.findViewById(android.R.id.text1)
        textView.text = schemeList[position].schemeName

        return view
    }
}

