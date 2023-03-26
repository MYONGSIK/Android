package com.myongsik.myongsikandroid.ui.adapter.food

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myongsik.myongsikandroid.R

// Home RV Header 부분
class HomeHeaderAdapter : RecyclerView.Adapter<HomeHeaderAdapter.HeaderViewHolder>() {

    class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeaderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.header_search_ranking, parent, false)
        return HeaderViewHolder(view)
    }
    override fun onBindViewHolder(holder: HeaderViewHolder, position: Int){}
    override fun getItemCount(): Int {
        return 1
    }
}