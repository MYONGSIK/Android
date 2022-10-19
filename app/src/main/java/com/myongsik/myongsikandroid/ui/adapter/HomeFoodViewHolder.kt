package com.myongsik.myongsikandroid.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import com.myongsik.myongsikandroid.data.model.FoodResult
import com.myongsik.myongsikandroid.data.model.TodayFoodResponse
import com.myongsik.myongsikandroid.data.model.WeekFoodResult
import com.myongsik.myongsikandroid.databinding.ItemHomeFoodBinding

//주간 음식 조회 리스트뷰 뷰홀더
class HomeFoodViewHolder(
    private val binding : ItemHomeFoodBinding
) : RecyclerView.ViewHolder(binding.root){

    fun bind(weekFoodResult: WeekFoodResult){
        val dayDate = weekFoodResult.toDay.substring(0, 4)
        val dayMonth = weekFoodResult.toDay.substring(5, 7)
        val dayDay = weekFoodResult.toDay.substring(8, 10)
        val day = weekFoodResult.dayOfTheWeek

        val date = "${dayDate}년 ${dayMonth}월 ${dayDay}일 $day"

        itemView.apply{
            binding.weekFoodDayOfWeekTv.text = date
            binding.weekFood1.text = "${weekFoodResult.lunchA[0]} "
            binding.weekFood2.text = "${weekFoodResult.lunchA[1]} "
            binding.weekFood3.text = "${weekFoodResult.lunchA[2]}"
            binding.weekFood4.text = "${weekFoodResult.lunchA[3]} "
            binding.weekFood5.text = "${weekFoodResult.lunchA[4]} "
            binding.weekFood6.text = "${weekFoodResult.lunchA[5]}"

            binding.weekAfternoon2Food1.text = "${weekFoodResult.lunchB[0]} "
            binding.weekAfternoon2Food2.text = "${weekFoodResult.lunchB[1]} "
            binding.weekAfternoon2Food3.text = "${weekFoodResult.lunchB[2]}"
            binding.weekAfternoon2Food4.text = "${weekFoodResult.lunchB[3]} "
            binding.weekAfternoon2Food5.text = "${weekFoodResult.lunchB[4]} "
            binding.weekAfternoon2Food6.text = "${weekFoodResult.lunchB[5]}"

            binding.weekEveningFood1.text = "${weekFoodResult.dinner[0]} "
            binding.weekEveningFood2.text = "${weekFoodResult.dinner[1]} "
            binding.weekEveningFood3.text = "${weekFoodResult.dinner[2]}"
            binding.weekEveningFood4.text = "${weekFoodResult.dinner[3]} "
            binding.weekEveningFood5.text = "${weekFoodResult.dinner[4]} "
            binding.weekEveningFood6.text = "${weekFoodResult.dinner[5]}"
        }
//        test 완료
//        binding.weekFoodAfternoonTv.setOnClickListener {
//            println(weekFoodResult.dayOfTheWeek)
//        }
    }
}