package com.myongsik.myongsikandroid.ui.adapter.food

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myongsik.myongsikandroid.databinding.HeaderSearchRankingBinding

class RankHeaderAdapter(listener: OnScrapViewHolderClick) : RecyclerView.Adapter<RankHeaderViewHolder>() {

    private val mCallback = listener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankHeaderViewHolder {
        return RankHeaderViewHolder(
            HeaderSearchRankingBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            mCallback
        )
    }
    override fun onBindViewHolder(holder: RankHeaderViewHolder, position: Int){

    }
    override fun getItemCount(): Int {
        return 1
    }
}