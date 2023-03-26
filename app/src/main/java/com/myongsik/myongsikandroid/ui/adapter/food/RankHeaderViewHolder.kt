package com.myongsik.myongsikandroid.ui.adapter.food

import androidx.recyclerview.widget.RecyclerView
import com.myongsik.myongsikandroid.databinding.HeaderSearchRankingBinding


class RankHeaderViewHolder(
    private val binding : HeaderSearchRankingBinding,
    private val clickCallback: OnScrapViewHolderClick,
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.itemSearchHashtag1.setOnClickListener{
            clickCallback.onHashtagGoodFoodClick()
        }
        binding.itemSearchHashtag2.setOnClickListener{
            clickCallback.onHashtagGoodCafeClick()
        }
        binding.itemSearchHashtag3.setOnClickListener{
            clickCallback.onHashtagGoodDrinkClick()
        }
        binding.itemSearchHashtag4.setOnClickListener{
            clickCallback.onHashtagGoodBreadClick()
        }
    }
}
