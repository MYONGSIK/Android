package com.myongsik.myongsikandroid.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.myongsik.myongsikandroid.data.model.food.FoodResult
import com.myongsik.myongsikandroid.data.model.kakao.Restaurant
import com.myongsik.myongsikandroid.databinding.ItemHomeTodayFoodBinding
import com.myongsik.myongsikandroid.databinding.ItemRestaurantFoodBinding
import com.myongsik.myongsikandroid.ui.viewmodel.MainViewModel

//카카오 api 불러온 리사이클러뷰 아이템 어댑터
class SearchFoodAdapter : ListAdapter<Restaurant, SearchFoodViewHolder>(BookDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchFoodViewHolder {
        return SearchFoodViewHolder(
            ItemRestaurantFoodBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        )
    }

    override fun onBindViewHolder(holder: SearchFoodViewHolder, position: Int) {
        val foodResult = currentList[position]
        holder.bind(foodResult)
    }

    companion object{
        private val BookDiffCallback = object : DiffUtil.ItemCallback<Restaurant>(){
            override fun areItemsTheSame(oldItem: Restaurant, newItem: Restaurant): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Restaurant, newItem: Restaurant): Boolean {
                return oldItem == newItem
            }
        }
    }


}