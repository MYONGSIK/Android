package com.myongsik.myongsikandroid.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import com.myongsik.myongsikandroid.data.model.FoodResult
import com.myongsik.myongsikandroid.data.model.TodayFoodResponse
import com.myongsik.myongsikandroid.data.model.WeekFoodResult
import com.myongsik.myongsikandroid.databinding.ItemHomeFoodBinding
import com.myongsik.myongsikandroid.databinding.ItemHomeTodayFoodBinding

//홈화면 일단위 음식 조회 리스트뷰 뷰홀더
class HomeTodayFoodViewHolder(
    private val binding : ItemHomeTodayFoodBinding
) : RecyclerView.ViewHolder(binding.root){

    fun bind(foodResult: FoodResult){
        val dayDate = foodResult.toDay.substring(0, 4)
        val dayMonth = foodResult.toDay.substring(5, 7)
        val dayDay = foodResult.toDay.substring(8, 10)
        val day = foodResult.dayOfTheWeek

        val date = "${dayDate}년 ${dayMonth}월 ${dayDay}일 $day"
        val type = foodResult.type ?: ""
        val dayType = "${foodResult.classification}${type}"

        itemView.apply{
//            binding.weekFoodDayOfWeekTv.text = date
            binding.todayDay.text = date //날짜+요일
            binding.todayTv.text = dayType //중식A, 중식B, 석식

            binding.todayFood1.text = foodResult.food1
            binding.todayFood2.text = foodResult.food2
            binding.todayFood3.text = foodResult.food3
            binding.todayFood4.text = foodResult.food4
            binding.todayFood5.text = foodResult.food5
            binding.todayFood6.text = foodResult.food6
        }
    }

}