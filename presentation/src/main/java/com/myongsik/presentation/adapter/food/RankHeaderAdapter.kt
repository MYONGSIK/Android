package com.myongsik.presentation.adapter.food

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myongsik.presentation.databinding.HeaderSearchRankingBinding

class RankHeaderAdapter(listener: OnScrapViewHolderClick, private val sortType: Int) :
    RecyclerView.Adapter<RankHeaderViewHolder>() {

    private val mCallback = listener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankHeaderViewHolder {
        return RankHeaderViewHolder(HeaderSearchRankingBinding.inflate(LayoutInflater.from(parent.context), parent, false), mCallback, sortType)
    }

    override fun onBindViewHolder(holder: RankHeaderViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 1
    }
}