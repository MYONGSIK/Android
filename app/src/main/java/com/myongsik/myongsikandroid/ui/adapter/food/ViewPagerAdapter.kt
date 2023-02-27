package com.myongsik.myongsikandroid.ui.adapter.food

import android.graphics.Color
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myongsik.myongsikandroid.databinding.ItemHomeTodayFoodBinding

//뷰페이저 어댑터
//주간 음식 조회에서 사용
class ViewPagerAdapter(
    private var weekFoodResult: MutableList<List<List<String>>>,
) : RecyclerView.Adapter<ViewPagerAdapter.Pager2ViewHolder>() {

    private lateinit var day: String
    private var builderWeekFood = SpannableStringBuilder()
    private var builderWeekBFood = SpannableStringBuilder()
    private var builderDinnerFood = SpannableStringBuilder()


    inner class Pager2ViewHolder(
        private val binding : ItemHomeTodayFoodBinding
    ) : RecyclerView.ViewHolder(binding.root){
        fun bind(weekFoodResult: List<List<String>>){
            Log.e("dfd", weekFoodResult.toString())
            val weekFood = "${weekFoodResult[0][0]} ${weekFoodResult[0][1]} ${weekFoodResult[0][2]} " +
                    "${weekFoodResult[0][3]} ${weekFoodResult[0][4]} ${weekFoodResult[0][5]}"
            builderWeekFood = SpannableStringBuilder(weekFood)
            val boldSpanWeekFood = ForegroundColorSpan(Color.parseColor("#274984"))
            builderWeekFood.setSpan(boldSpanWeekFood, 0, weekFoodResult[0][0].toString().length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

            val weekBFood = "${weekFoodResult[1][0]} ${weekFoodResult[1][1]} ${weekFoodResult[1][2]} " +
                    "${weekFoodResult[1][3]} ${weekFoodResult[1][4]} ${weekFoodResult[1][5]}"
            builderWeekBFood = SpannableStringBuilder(weekBFood)
            val boldSpanWeekBFood = ForegroundColorSpan(Color.parseColor("#274984"))
            builderWeekBFood.setSpan(boldSpanWeekBFood, 0, weekFoodResult[1][0].toString().length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

            val dinner = "${weekFoodResult[2][0]} ${weekFoodResult[2][1]} ${weekFoodResult[2][2]} " +
                    "${weekFoodResult[2][3]} ${weekFoodResult[2][4]} ${weekFoodResult[2][5]}"
            builderDinnerFood = SpannableStringBuilder(dinner)
            val boldSpanDinner = ForegroundColorSpan(Color.parseColor("#274984"))
            builderDinnerFood.setSpan(boldSpanDinner, 0, weekFoodResult[2][0].toString().length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)





//


//            val btn : ConstraintLayout = itemView.findViewById(R.id.today_hate_cl_lunch_a)

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
    }

//    fun getGoodChange() {
//                binding.todayAfternoonGoodTv.setTextColor(Color.parseColor("#274984"))
//                binding.todayAfternoonHateTv.setTextColor(Color.parseColor("#717171"))
//                binding.todayAfternoonGoodIv.setColorFilter(Color.parseColor("#274984"))
//                binding.todayAfternoonHateIv.setColorFilter(Color.parseColor("#717171"))
//            }
//
//            fun getHateChange() {
//                binding.todayAfternoonHateTv.setTextColor(Color.parseColor("#274984"))
//                binding.todayAfternoonGoodTv.setTextColor(Color.parseColor("#717171"))
//                binding.todayAfternoonGoodIv.setColorFilter(Color.parseColor("#717171"))
//                binding.todayAfternoonHateIv.setColorFilter(Color.parseColor("#274984"))            }
//
//            fun defaultChange() {
//                binding.todayAfternoonGoodTv.setTextColor(Color.parseColor("#717171"))
//                binding.todayAfternoonHateTv.setTextColor(Color.parseColor("#717171"))
//                binding.todayAfternoonGoodIv.setColorFilter(Color.parseColor("#717171"))
//                binding.todayAfternoonHateIv.setColorFilter(Color.parseColor("#717171"))
//
//            }




    override fun getItemCount(): Int {
        return weekFoodResult.size
    }
}