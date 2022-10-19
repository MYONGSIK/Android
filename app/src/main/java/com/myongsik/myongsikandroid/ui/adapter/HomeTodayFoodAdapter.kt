package com.myongsik.myongsikandroid.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.myongsik.myongsikandroid.data.model.FoodResult
import com.myongsik.myongsikandroid.data.model.WeekFoodResult

import com.myongsik.myongsikandroid.databinding.ItemHomeFoodBinding
import com.myongsik.myongsikandroid.databinding.ItemHomeTodayFoodBinding
import com.myongsik.myongsikandroid.ui.viewmodel.MainViewModel

//홈화면 일단위 음식 조회 리스트뷰 어댑터
class HomeTodayFoodAdapter : ListAdapter<FoodResult, HomeTodayFoodViewHolder>(BookDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeTodayFoodViewHolder {
        return HomeTodayFoodViewHolder(
            ItemHomeTodayFoodBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        )
    }

    override fun onBindViewHolder(holder: HomeTodayFoodViewHolder, position: Int) {
        val foodResult = currentList[position]
        holder.bind(foodResult)
    }


    companion object{
        private val BookDiffCallback = object : DiffUtil.ItemCallback<FoodResult>(){
            override fun areItemsTheSame(oldItem: FoodResult, newItem: FoodResult): Boolean {
                return oldItem.toDay == newItem.toDay
            }

            override fun areContentsTheSame(oldItem: FoodResult, newItem: FoodResult): Boolean {
                return oldItem == newItem
            }
        }
    }


}