package com.myongsik.myongsikandroid.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.myongsik.myongsikandroid.data.model.FoodResult
import com.myongsik.myongsikandroid.data.model.WeekFoodResult

import com.myongsik.myongsikandroid.databinding.ItemHomeFoodBinding

class HomeFoodAdapter : ListAdapter<WeekFoodResult, HomeFoodViewHolder>(BookDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeFoodViewHolder {
        return HomeFoodViewHolder(
            ItemHomeFoodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: HomeFoodViewHolder, position: Int) {
        val weekFoodResult = currentList[position]
        holder.bind(weekFoodResult)
    }

    companion object{
        private val BookDiffCallback = object : DiffUtil.ItemCallback<WeekFoodResult>(){
            override fun areItemsTheSame(oldItem: WeekFoodResult, newItem: WeekFoodResult): Boolean {
                return oldItem.toDay == newItem.toDay
            }

            override fun areContentsTheSame(oldItem: WeekFoodResult, newItem: WeekFoodResult): Boolean {
                return oldItem == newItem
            }

        }
    }


}