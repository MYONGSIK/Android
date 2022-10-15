package com.myongsik.myongsikandroid.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import com.myongsik.myongsikandroid.data.model.FoodResult
import com.myongsik.myongsikandroid.data.model.TodayFoodResponse
import com.myongsik.myongsikandroid.databinding.ItemHomeFoodBinding

class HomeFoodViewHolder(
    private val binding : ItemHomeFoodBinding
) : RecyclerView.ViewHolder(binding.root){


    fun bind(foodResult: FoodResult){
        val date = foodResult.toDay.substring(0, 10)

        itemView.apply{
            binding.weekFoodDayOfWeekTv.text = date
            binding.weekFood1.text = foodResult.food1
            binding.weekFood2.text = foodResult.food1
            binding.weekFood3.text = foodResult.food1
            binding.weekFood4.text = foodResult.food1
            binding.weekFood5.text = foodResult.food1
            binding.weekFood6.text = foodResult.food1
        }
    }
}