package com.myongsik.myongsikandroid.ui.adapter.food

import android.graphics.Color
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myongsik.myongsikandroid.databinding.ItemHomeTodayFoodBinding

//뷰페이저 어댑터
//주간 음식 조회에서 사용
class ViewPagerAreaAdapter(
    private var weekFoodResult: List<String>,
) : RecyclerView.Adapter<ViewPagerAreaAdapter.Pager2ViewHolder>() {

    inner class Pager2ViewHolder(
        private val binding : ItemHomeTodayFoodBinding
    ) : RecyclerView.ViewHolder(binding.root){
        fun bind(weekFoodResult: String){


            val dinner = "${weekFoodResult[0]} ${weekFoodResult[1]} ${weekFoodResult[2]} " +
                    "${weekFoodResult[3]} ${weekFoodResult[4]} ${weekFoodResult[5]} "

            val builderDinnerFood = SpannableStringBuilder(dinner)
            val boldSpanDinner = ForegroundColorSpan(Color.parseColor("#274984"))
            builderDinnerFood.setSpan(boldSpanDinner, 0, weekFoodResult[0].toString().length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

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

            itemView.apply{
                // 석식만 하고 있음
                binding.homeLunchAV.visibility = View.GONE
                binding.homeLunchBV.visibility = View.GONE
                binding.todayDayLunchA.visibility = View.GONE
                binding.todayDayLunchB.visibility = View.GONE
                binding.todayFood1.visibility = View.GONE
                binding.todayFoodLunchB.visibility = View.GONE
                binding.todayLineIv.visibility = View.GONE
                binding.todayLunchBLineIv.visibility = View.GONE
                binding.todayGoodCl.visibility = View.GONE
                binding.todayGoodClLunchB.visibility = View.GONE
                binding.todayHateCl.visibility = View.GONE
                binding.todayHateClLunchB.visibility = View.GONE
                binding.todayHateClLunchA.visibility = View.GONE
                binding.todayHateClLunchB.visibility = View.GONE


                binding.todayFoodAfternoon.text = builderDinnerFood

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

    override fun getItemCount(): Int {
        return weekFoodResult.size
    }
}