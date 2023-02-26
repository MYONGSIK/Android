package com.myongsik.myongsikandroid.ui.adapter.food

import android.graphics.Color
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet.Layout
import androidx.recyclerview.widget.RecyclerView
import com.myongsik.myongsikandroid.R
import com.myongsik.myongsikandroid.data.model.food.FoodResult
import com.myongsik.myongsikandroid.data.model.food.OnLoveClick
import com.myongsik.myongsikandroid.data.model.food.OnLoveFoodClick
import com.myongsik.myongsikandroid.data.model.food.WeekFoodResult
import com.myongsik.myongsikandroid.databinding.ItemHomeTodayFoodBinding
import com.myongsik.myongsikandroid.ui.viewmodel.MainViewModel
import com.myongsik.myongsikandroid.util.Constant

//뷰페이저 어댑터
//주간 음식 조회에서 사용
class ViewPagerAdapter(
    private var weekFoodResult: List<WeekFoodResult>,
) : RecyclerView.Adapter<ViewPagerAdapter.Pager2ViewHolder>() {

    private lateinit var day: String

    inner class Pager2ViewHolder(
        private val binding : ItemHomeTodayFoodBinding
    ) : RecyclerView.ViewHolder(binding.root){
        fun bind(weekFoodResult: WeekFoodResult){
            val dayDate = weekFoodResult.toDay.substring(0, 4)
            val dayMonth = weekFoodResult.toDay.substring(5, 7)
            val dayDay = weekFoodResult.toDay.substring(8, 10)
            day = weekFoodResult.dayOfTheWeek

            val date = "${dayDate}년 ${dayMonth}월 ${dayDay}일"


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

            fun getGoodChange() {
                binding.todayAfternoonGoodTv.setTextColor(Color.parseColor("#274984"))
                binding.todayAfternoonHateTv.setTextColor(Color.parseColor("#717171"))
                binding.todayAfternoonGoodIv.setColorFilter(Color.parseColor("#274984"))
                binding.todayAfternoonHateIv.setColorFilter(Color.parseColor("#717171"))
            }

            fun getHateChange() {
                binding.todayAfternoonHateTv.setTextColor(Color.parseColor("#274984"))
                binding.todayAfternoonGoodTv.setTextColor(Color.parseColor("#717171"))
                binding.todayAfternoonGoodIv.setColorFilter(Color.parseColor("#717171"))
                binding.todayAfternoonHateIv.setColorFilter(Color.parseColor("#274984"))            }

            fun defaultChange() {
                binding.todayAfternoonGoodTv.setTextColor(Color.parseColor("#717171"))
                binding.todayAfternoonHateTv.setTextColor(Color.parseColor("#717171"))
                binding.todayAfternoonGoodIv.setColorFilter(Color.parseColor("#717171"))
                binding.todayAfternoonHateIv.setColorFilter(Color.parseColor("#717171"))

            }


            val btn : ConstraintLayout = itemView.findViewById(R.id.today_hate_cl_lunch_a)

            itemView.apply{
                binding.todayFood1.text = builderWeekFood
                binding.todayFoodLunchB.text = builderWeekBFood
                binding.todayFoodAfternoon.text = builderDinnerFood

                binding.todayGoodCl.setOnClickListener{
//                    if(Constant.LUNCH_A_GOOD == "good"){
//                        Constant.LUNCH_A_GOOD = ""
//                        var foodResult : FoodResult? = null
//                        foodResult?.
//                        foodResult?.food1
//                        mainViewModel.saveLunchEvaluation(weekFoodResult, "")
//                    }else{
//                        Constant.LUNCH_A_GOOD = "good"
//                        mainViewModel.saveLunchEvaluation(weekFoodResult, "good")
//                    }
                }
                binding.todayGoodClLunchB.setOnClickListener{

                }
                binding.todayGoodClAfternoon.setOnClickListener{

                }





            }
        }
    }

    private fun getEvaluation(foodResult: Any) {

    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Pager2ViewHolder {
        return Pager2ViewHolder(
            ItemHomeTodayFoodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }


    override fun onBindViewHolder(holder: Pager2ViewHolder, position: Int) {
        holder.bind(weekFoodResult[position])
        // 요일마다 버튼 숨기기
        when(day){
            "월요일" -> {
                if(position == 0){
//                    holder.binding
                }
            }
        }
    }




    override fun getItemCount(): Int {
        return weekFoodResult.size
    }
}