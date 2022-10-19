package com.myongsik.myongsikandroid.ui.adapter

import android.graphics.Color
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
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

        var weekFood : String = ""
        var weekBFood : String = ""
        var dinner : String = ""

        weekFood = "${weekFoodResult.lunchA[0]} ${weekFoodResult.lunchA[1]} ${weekFoodResult.lunchA[2]}\n" +
                "${weekFoodResult.lunchA[3]} ${weekFoodResult.lunchA[4]} ${weekFoodResult.lunchA[5]}"

        weekBFood = "${weekFoodResult.lunchB[0]} ${weekFoodResult.lunchB[1]} ${weekFoodResult.lunchB[2]}\n" +
                "${weekFoodResult.lunchB[3]}  ${weekFoodResult.lunchB[4]}  ${weekFoodResult.lunchB[5]} "

        dinner = "${weekFoodResult.dinner[0]} ${weekFoodResult.dinner[1]} ${weekFoodResult.dinner[2]}\n" +
                "${weekFoodResult.dinner[3]} ${weekFoodResult.dinner[4]} ${weekFoodResult.dinner[5]} "

        // 3. SpannableStringBuilder 타입으로 변환
        val builderWeekFood = SpannableStringBuilder(weekFood)

        // 4-1. index=0 에 해당하는 문자열(0)에 볼드체적용
        val boldSpanWeekFood = ForegroundColorSpan(Color.parseColor("#274984"))
        builderWeekFood.setSpan(boldSpanWeekFood, 0, weekFoodResult.lunchA[0].length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        // 3. SpannableStringBuilder 타입으로 변환
        val builderWeekBFood = SpannableStringBuilder(weekBFood)

        // 4-1. index=0 에 해당하는 문자열(0)에 볼드체적용
        val boldSpanWeekBFood = ForegroundColorSpan(Color.parseColor("#274984"))
        builderWeekBFood.setSpan(boldSpanWeekBFood, 0, weekFoodResult.lunchB[0].length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        // 3. SpannableStringBuilder 타입으로 변환
        val builderDinnerFood = SpannableStringBuilder(dinner)

        // 4-1. index=0 에 해당하는 문자열(0)에 볼드체적용
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