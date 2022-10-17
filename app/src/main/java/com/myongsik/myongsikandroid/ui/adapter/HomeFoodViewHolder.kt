package com.myongsik.myongsikandroid.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import com.myongsik.myongsikandroid.data.model.FoodResult
import com.myongsik.myongsikandroid.data.model.TodayFoodResponse
import com.myongsik.myongsikandroid.data.model.WeekFoodResult
import com.myongsik.myongsikandroid.databinding.ItemHomeFoodBinding

class HomeFoodViewHolder(
    private val binding : ItemHomeFoodBinding
) : RecyclerView.ViewHolder(binding.root){

//    private var day : String? = null
//    private var dayDate : String? = null
//    private var dayMonth : String? = null
//    private var dayDay : String? = null

    fun bind(weekFoodResult: WeekFoodResult){
        val dayDate = weekFoodResult.toDay.substring(0, 4)
        val dayMonth = weekFoodResult.toDay.substring(5, 7)
        val dayDay = weekFoodResult.toDay.substring(8, 10)
        val day = weekFoodResult.dayOfTheWeek

        val date = "${dayDate}년 ${dayMonth}월 ${dayDay}일 $day"

        itemView.apply{
            binding.weekFoodDayOfWeekTv.text = date
            binding.weekFood1.text = weekFoodResult.lunch1
            binding.weekFood2.text = weekFoodResult.lunch2
            binding.weekFood3.text = weekFoodResult.lunch3
            binding.weekFood4.text = weekFoodResult.lunch4
            binding.weekFood5.text = weekFoodResult.lunch5
            binding.weekFood6.text = weekFoodResult.lunch6

            binding.weekEveningFood1.text = weekFoodResult.dinner1
            binding.weekEveningFood2.text = weekFoodResult.dinner2
            binding.weekEveningFood3.text = weekFoodResult.dinner3
            binding.weekEveningFood4.text = weekFoodResult.dinner4
            binding.weekEveningFood5.text = weekFoodResult.dinner5
            binding.weekEveningFood6.text = weekFoodResult.dinner6

        }
    }
}