package com.myongsik.myongsikandroid.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import com.myongsik.myongsikandroid.data.model.FoodResult
import com.myongsik.myongsikandroid.data.model.TodayFoodResponse
import com.myongsik.myongsikandroid.databinding.ItemHomeFoodBinding

class HomeFoodViewHolder(
    private val binding : ItemHomeFoodBinding
) : RecyclerView.ViewHolder(binding.root){

//    private var day : String? = null
//    private var dayDate : String? = null
//    private var dayMonth : String? = null
//    private var dayDay : String? = null

    fun bind(foodResult: FoodResult){
        val dayDate = foodResult.toDay.substring(0, 4)
        val dayMonth = foodResult.toDay.substring(5, 7)
        val dayDay = foodResult.toDay.substring(8, 10)
        val day = foodResult.dayOfTheWeek

        val date = "${dayDate}년 ${dayMonth}월 ${dayDay}일 $day"

        itemView.apply{
            binding.weekFoodDayOfWeekTv.text = date
            binding.weekFood1.text = foodResult.food1
            binding.weekFood2.text = foodResult.food2
            binding.weekFood3.text = foodResult.food3
            binding.weekFood4.text = foodResult.food4
            binding.weekFood5.text = foodResult.food5
            binding.weekFood6.text = foodResult.food6
        }
    }
}