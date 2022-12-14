package com.myongsik.myongsikandroid.ui.adapter.food

import android.graphics.Color
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import androidx.recyclerview.widget.RecyclerView
import com.myongsik.myongsikandroid.data.model.food.FoodResult
import com.myongsik.myongsikandroid.databinding.ItemHomeTodayFoodBinding
import com.myongsik.myongsikandroid.ui.viewmodel.MainViewModel
import com.myongsik.myongsikandroid.util.Constant.DINNER
import com.myongsik.myongsikandroid.util.Constant.LUNCH_A_GOOD
import com.myongsik.myongsikandroid.util.Constant.LUNCH_B_GOOD

//홈화면 일단위 음식 조회 리스트뷰 뷰홀더
class HomeTodayFoodViewHolder(
    private val binding : ItemHomeTodayFoodBinding,
    private val mainViewModel: MainViewModel
) : RecyclerView.ViewHolder(binding.root){

    fun bind(foodResult: FoodResult){
        val dayDate = foodResult.toDay.substring(0, 4)
        val dayMonth = foodResult.toDay.substring(5, 7)
        val dayDay = foodResult.toDay.substring(8, 10)
        val day = foodResult.dayOfTheWeek

        val date = "${dayDate}년 ${dayMonth}월 ${dayDay}일 $day"
        val type = foodResult.type ?: ""
        val dayType = "${foodResult.classification}${type}"

        val todayFood = "${foodResult.food1} ${foodResult.food2} ${foodResult.food3} " +
                "${foodResult.food4} ${foodResult.food5} ${foodResult.food6} "

        //첫번째 음식은 파란색으로
        val builderWeekFood = SpannableStringBuilder(todayFood)

        val boldSpanWeekFood = ForegroundColorSpan(Color.parseColor("#274984"))
        builderWeekFood.setSpan(boldSpanWeekFood, 0, foodResult.food1.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)


        itemView.apply{
//            binding.weekFoodDayOfWeekTv.text = date
            binding.todayDay.text = date //날짜+요일
            binding.todayTv.text = dayType //중식A, 중식B, 석식
            binding.todayFood1.text = builderWeekFood
        }

        //오늘 음식 조회했을 때 현재의 DataStore 를 판단하여 색을 다르게 저장
        getEvaluation(foodResult)

        //맛있어요 버튼 클릭했을 때
        binding.todayGoodCl.setOnClickListener {
            if(foodResult.type=="A"){
                if(LUNCH_A_GOOD == "good"){
                    LUNCH_A_GOOD = ""
                    mainViewModel.saveLunchEvaluation(foodResult, "")
                }else{
                    LUNCH_A_GOOD = "good"
                    mainViewModel.saveLunchEvaluation(foodResult, "good")
                }
            }
            if(foodResult.type=="B"){
                if(LUNCH_B_GOOD == "good"){
                    LUNCH_B_GOOD = ""
                    mainViewModel.saveLunchEvaluation(foodResult, "")
                }else{
                    LUNCH_B_GOOD = "good"
                    mainViewModel.saveLunchEvaluation(foodResult, "good")
                }
            }
            if(foodResult.type== null){
                if(DINNER == "good"){
                    DINNER = ""
                    mainViewModel.saveLunchEvaluation(foodResult, "")
                }else{
                    DINNER = "good"
                    mainViewModel.saveLunchEvaluation(foodResult, "good")
                }
            }
            getEvaluation(foodResult)
        }

        //맛없어요 버튼 클릭했을 때
        binding.todayHateCl.setOnClickListener {
            if(foodResult.type=="A"){
                if(LUNCH_A_GOOD == "hate"){
                    LUNCH_A_GOOD = ""
                    mainViewModel.saveLunchEvaluation(foodResult, "")
                }else{
                    LUNCH_A_GOOD = "hate"
                    mainViewModel.saveLunchEvaluation(foodResult, "hate")
                }
            }
            if(foodResult.type=="B"){
                if(LUNCH_B_GOOD == "hate"){
                    LUNCH_B_GOOD = ""
                    mainViewModel.saveLunchEvaluation(foodResult, "")
                }else{
                    LUNCH_B_GOOD = "hate"
                    mainViewModel.saveLunchEvaluation(foodResult, "hate")
                }
            }
            if(foodResult.type==null){
                if(DINNER == "hate"){
                    DINNER = ""
                    mainViewModel.saveLunchEvaluation(foodResult, "")
                }else{
                    DINNER = "hate"
                    mainViewModel.saveLunchEvaluation(foodResult, "hate")
                }
            }
            getEvaluation(foodResult)
        }
    }

    //현재 맛있어요, 맛없어요 상태를 판단하여 색을 다르게
    private fun getEvaluation(foodResult: FoodResult){
        if(foodResult.type == "A"){
            when (LUNCH_A_GOOD) {
                "good" -> {
                    getGoodChange()
                }
                "hate" -> {
                    getHateChange()
                }
                else -> {
                    defaultChange()
                }
            }
        }
        if(foodResult.type == "B"){
            when (LUNCH_B_GOOD) {
                "good" -> {
                    getGoodChange()
                }
                "hate" -> {
                    getHateChange()
                }
                else -> {
                    defaultChange()
                }
            }
        }
        if(foodResult.type == null){
            when (DINNER) {
                "good" -> {
                    getGoodChange()
                }
                "hate" -> {
                    getHateChange()
                }
                else -> {
                    defaultChange()
                }
            }
        }
    }

    private fun getGoodChange() {
        binding.todayAfternoonGoodTv.setTextColor(Color.parseColor("#274984"))
        binding.todayAfternoonHateTv.setTextColor(Color.parseColor("#717171"))
        binding.todayAfternoonGoodIv.setColorFilter(Color.parseColor("#274984"))
        binding.todayAfternoonHateIv.setColorFilter(Color.parseColor("#717171"))
    }

    private fun getHateChange() {
        binding.todayAfternoonHateTv.setTextColor(Color.parseColor("#274984"))
        binding.todayAfternoonGoodTv.setTextColor(Color.parseColor("#717171"))
        binding.todayAfternoonGoodIv.setColorFilter(Color.parseColor("#717171"))
        binding.todayAfternoonHateIv.setColorFilter(Color.parseColor("#274984"))
    }

    private fun defaultChange(){
        binding.todayAfternoonGoodTv.setTextColor(Color.parseColor("#717171"))
        binding.todayAfternoonHateTv.setTextColor(Color.parseColor("#717171"))
        binding.todayAfternoonGoodIv.setColorFilter(Color.parseColor("#717171"))
        binding.todayAfternoonHateIv.setColorFilter(Color.parseColor("#717171"))
    }
}