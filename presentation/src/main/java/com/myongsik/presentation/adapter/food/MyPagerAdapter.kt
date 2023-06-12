package com.myongsik.presentation.adapter.food

import android.graphics.Color
import android.graphics.Typeface
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myongsik.myongsikandroid.databinding.ItemHomeTodayFoodBinding

class MyPagerAdapter(
    private val itemList: List<List<List<String>>>,
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var builderDinnerFood = SpannableStringBuilder()

    inner class ItemViewHolder(
        val binding: ItemHomeTodayFoodBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(weekFoodResult: List<List<String>>) {

            val weekFoodA =
                "${weekFoodResult[0][0]} ${weekFoodResult[0][1]} ${weekFoodResult[0][2]} " +
                        "${weekFoodResult[0][3]} ${weekFoodResult[0][4]} ${weekFoodResult[0][5]}"

            val builderWeekFoodA = SpannableStringBuilder(weekFoodA).apply {
                setSpan(
                    ForegroundColorSpan(Color.parseColor("#0A45CA")),
                    0,
                    weekFoodResult[0][0].length,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                setSpan(
                    StyleSpan(Typeface.BOLD),
                    0,
                    weekFoodResult[0][0].length,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }

            val weekFoodB =
                "${weekFoodResult[1][0]} ${weekFoodResult[1][1]} ${weekFoodResult[1][2]} " +
                        "${weekFoodResult[1][3]} ${weekFoodResult[1][4]} ${weekFoodResult[1][5]}"
            val builderWeekFoodB = SpannableStringBuilder(weekFoodB).apply {
                setSpan(
                    ForegroundColorSpan(Color.parseColor("#0A45CA")),
                    0,
                    weekFoodResult[1][0].length,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                setSpan(
                    StyleSpan(Typeface.BOLD),
                    0,
                    weekFoodResult[1][0].length,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }

            if (weekFoodResult.size.toString() == "3") {
                val dinner =
                    "${weekFoodResult[2][0]} ${weekFoodResult[2][1]} ${weekFoodResult[2][2]} " +
                            "${weekFoodResult[2][3]} ${weekFoodResult[2][4]} ${weekFoodResult[2][5]}"
                builderDinnerFood = SpannableStringBuilder(dinner).apply {
                    setSpan(
                        ForegroundColorSpan(Color.parseColor("#0A45CA")),
                        0,
                        weekFoodResult[2][0].length,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    setSpan(
                        StyleSpan(Typeface.BOLD),
                        0,
                        weekFoodResult[2][0].length,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }
            }

            itemView.apply {
                if (MyongsikApplication.prefs.getUserArea() == "H"){
                    binding.weekFoodLunchAV.text = "조식"
                    binding.weekFoodAfternoonTv.text = "중식"
                }
                if (MyongsikApplication.prefs.getUserArea() == "M"){
                    binding.weekFoodLunchAV.text = "백반"
                    binding.weekFoodLunchBTv.text = "샐러드"
                    binding.weekFoodAfternoonTv.text = "볶음밥"
                }
                // text 설정, 자캠의 경우는 lunchB 지우기
                binding.todayFood1.text = builderWeekFoodA
                if (weekFoodResult.size.toString() == "3") {
                    binding.todayFoodLunchB.text = builderWeekFoodB
                    binding.todayFoodAfternoon.text = builderDinnerFood
                } else {
                    // 자캠 경우 lunchB가 디너가됨
                    binding.todayFoodAfternoon.text = builderWeekFoodB

                    binding.homeLunchBV.visibility = GONE
                    binding.todayDayLunchB.visibility = GONE
                    binding.todayFoodLunchB.visibility = GONE
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemHomeTodayFoodBinding.inflate(inflater, parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemViewHolder = holder as ItemViewHolder
        itemViewHolder.bind(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}
