package com.myongsik.myongsikandroid.ui.adapter.food

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.myongsik.myongsikandroid.data.model.food.GetRankRestaurant
import com.myongsik.myongsikandroid.data.model.food.OnSearchViewHolderClick
import com.myongsik.myongsikandroid.databinding.ItemRestaurantRankingBinding

class RankRestaurantAdapter() : ListAdapter<GetRankRestaurant, RankRestaurantViewHolder>(BookDiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankRestaurantViewHolder {
        return RankRestaurantViewHolder(
            ItemRestaurantRankingBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        )
    }

    override fun onBindViewHolder(holder: RankRestaurantViewHolder, position: Int) {
        val foodResult = currentList[position]
        holder.bind(foodResult)
    }

    companion object {
        private val BookDiffCallback = object : DiffUtil.ItemCallback<GetRankRestaurant>() {
            override fun areItemsTheSame(oldItem: GetRankRestaurant, newItem: GetRankRestaurant): Boolean {
                return oldItem.storeId == newItem.storeId
            }

            override fun areContentsTheSame(oldItem: GetRankRestaurant, newItem: GetRankRestaurant): Boolean {
                return oldItem == newItem
            }
        }
    }
}