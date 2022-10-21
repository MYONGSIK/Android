package com.myongsik.myongsikandroid.ui.adapter

import android.graphics.Color
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import androidx.recyclerview.widget.RecyclerView
import com.myongsik.myongsikandroid.data.model.WeekFoodResult
import com.myongsik.myongsikandroid.databinding.ItemHomeFoodBinding

//주간 음식 조회 리스트뷰 뷰홀더
//ViewPager 로 변경하면서 안쓰임
class HomeFoodViewHolder(
    private val binding : ItemHomeFoodBinding
) : RecyclerView.ViewHolder(binding.root){

    fun bind(weekFoodResult: WeekFoodResult){
        val dayDate = weekFoodResult.toDay.substring(0, 4)
        val dayMonth = weekFoodResult.toDay.substring(5, 7)
        val dayDay = weekFoodResult.toDay.substring(8, 10)
        val day = weekFoodResult.dayOfTheWeek

        val date = "${dayDate}년 ${dayMonth}월 ${dayDay}일 $day"

        val weekFood = "${weekFoodResult.lunchA[0]} ${weekFoodResult.lunchA[1]} ${weekFoodResult.lunchA[2]} " +
                "${weekFoodResult.lunchA[3]} ${weekFoodResult.lunchA[4]} ${weekFoodResult.lunchA[5]}"

        val weekBFood = "${weekFoodResult.lunchB[0]} ${weekFoodResult.lunchB[1]} ${weekFoodResult.lunchB[2]} " +
                "${weekFoodResult.lunchB[3]}  ${weekFoodResult.lunchB[4]}  ${weekFoodResult.lunchB[5]} "

        val dinner = "${weekFoodResult.dinner[0]} ${weekFoodResult.dinner[1]} ${weekFoodResult.dinner[2]} " +
                "${weekFoodResult.dinner[3]} ${weekFoodResult.dinner[4]} ${weekFoodResult.dinner[5]} "

        val builderWeekFood = SpannableStringBuilder(weekFood)

        val boldSpanWeekFood = ForegroundColorSpan(Color.parseColor("#274984"))
        builderWeekFood.setSpan(boldSpanWeekFood, 0, weekFoodResult.lunchA[0].length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        val builderWeekBFood = SpannableStringBuilder(weekBFood)

        val boldSpanWeekBFood = ForegroundColorSpan(Color.parseColor("#274984"))
        builderWeekBFood.setSpan(boldSpanWeekBFood, 0, weekFoodResult.lunchB[0].length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        val builderDinnerFood = SpannableStringBuilder(dinner)

        val boldSpanDinner = ForegroundColorSpan(Color.parseColor("#274984"))
        builderDinnerFood.setSpan(boldSpanDinner, 0, weekFoodResult.dinner[0].length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        itemView.apply{
            binding.weekFoodDayOfWeekTv.text = date
            binding.weekFood.text = builderWeekFood

            binding.weekAfternoon2Food1.text = builderWeekBFood

            binding.weekEveningFood1.text = builderDinnerFood
        }
    }

}