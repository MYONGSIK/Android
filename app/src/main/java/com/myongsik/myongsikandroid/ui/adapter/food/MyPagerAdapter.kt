package com.myongsik.myongsikandroid.ui.adapter.food

import android.graphics.Color
import android.graphics.Typeface
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.myongsik.myongsikandroid.R
import com.myongsik.myongsikandroid.databinding.ItemHomeTodayFoodBinding
import com.myongsik.myongsikandroid.ui.viewmodel.food.HomeViewModel
import com.myongsik.myongsikandroid.util.Constant.DINNER
import com.myongsik.myongsikandroid.util.Constant.DINNER_H
import com.myongsik.myongsikandroid.util.Constant.DINNER_S
import com.myongsik.myongsikandroid.util.Constant.LUNCH_A_GOOD
import com.myongsik.myongsikandroid.util.Constant.LUNCH_A_GOOD_H
import com.myongsik.myongsikandroid.util.Constant.LUNCH_A_GOOD_S
import com.myongsik.myongsikandroid.util.Constant.LUNCH_B_GOOD
import com.myongsik.myongsikandroid.util.DialogUtils
import com.myongsik.myongsikandroid.util.MyongsikApplication
import java.util.*

class MyPagerAdapter(
    private val itemList: List<List<List<String>>>,
    private val homeViewModel: HomeViewModel
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
                    binding.todayGoodClLunchB.visibility = GONE
                    binding.todayGoodCl.visibility = GONE
                    binding.todayGoodClAfternoon.visibility = GONE
                    binding.todayHateClLunchB.visibility = GONE
                    binding.todayHateCl.visibility = GONE
                    binding.todayHateClLunchA.visibility = GONE
                    binding.todayLunchBLineIv.visibility = GONE
                    binding.todayLineIv.visibility = GONE
                    binding.todayLineIvAfternoon.visibility = GONE

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
                    binding.todayLunchBLineIv.visibility = GONE
                    binding.todayGoodClLunchB.visibility = GONE
                    binding.todayLunchBGoodTv.visibility = GONE
                    binding.todayLunchBGoodIv.visibility = GONE
                    binding.todayLunchBHateIv.visibility = GONE
                    binding.todayLunchBHateTv.visibility = GONE
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
        val today = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
        val isItemSelected = position == (today - 2)
        val colorResId = if (isItemSelected) R.color.home_yes_color else R.color.home_not_color
        val color = ContextCompat.getColor(holder.itemView.context, colorResId)
        itemViewHolder.binding.todayAfternoonGoodTv.setTextColor(color)
        itemViewHolder.binding.todayAfternoonHateTv.setTextColor(color)
        itemViewHolder.binding.todayLunchAGoodTv.setTextColor(color)
        itemViewHolder.binding.todayLunchBGoodTv.setTextColor(color)
        itemViewHolder.binding.todayLunchAHateTv.setTextColor(color)
        itemViewHolder.binding.todayLunchBHateTv.setTextColor(color)
        itemViewHolder.binding.todayLunchAGoodIv.setColorFilter(color)
        itemViewHolder.binding.todayLunchAHateIv.setColorFilter(color)
        itemViewHolder.binding.todayLunchBGoodIv.setColorFilter(color)
        itemViewHolder.binding.todayLunchBHateIv.setColorFilter(color)
        itemViewHolder.binding.todayAfternoonGoodIv.setColorFilter(color)
        itemViewHolder.binding.todayAfternoonHateIv.setColorFilter(color)

        fun getGoodChangeLunchA() {
            itemViewHolder.binding.todayLunchAGoodTv.setTextColor(Color.parseColor("#274984"))
            itemViewHolder.binding.todayLunchAHateTv.setTextColor(Color.parseColor("#CECECE"))
            itemViewHolder.binding.todayLunchAGoodIv.setColorFilter(Color.parseColor("#274984"))
            itemViewHolder.binding.todayLunchAHateIv.setColorFilter(Color.parseColor("#CECECE"))
        }

        fun getGoodChangeLunchB() {
            itemViewHolder.binding.todayLunchBGoodTv.setTextColor(Color.parseColor("#274984"))
            itemViewHolder.binding.todayLunchBHateTv.setTextColor(Color.parseColor("#CECECE"))
            itemViewHolder.binding.todayLunchBGoodIv.setColorFilter(Color.parseColor("#274984"))
            itemViewHolder.binding.todayLunchBHateIv.setColorFilter(Color.parseColor("#CECECE"))
        }

        fun getGoodChangeDinner() {
            itemViewHolder.binding.todayAfternoonGoodTv.setTextColor(Color.parseColor("#274984"))
            itemViewHolder.binding.todayAfternoonHateTv.setTextColor(Color.parseColor("#CECECE"))
            itemViewHolder.binding.todayAfternoonGoodIv.setColorFilter(Color.parseColor("#274984"))
            itemViewHolder.binding.todayAfternoonHateIv.setColorFilter(Color.parseColor("#CECECE"))
        }

        fun getHateChangeLunchA() {
            itemViewHolder.binding.todayLunchAHateTv.setTextColor(Color.parseColor("#274984"))
            itemViewHolder.binding.todayLunchAGoodTv.setTextColor(Color.parseColor("#CECECE"))
            itemViewHolder.binding.todayLunchAGoodIv.setColorFilter(Color.parseColor("#CECECE"))
            itemViewHolder.binding.todayLunchAHateIv.setColorFilter(Color.parseColor("#274984"))
        }

        fun getHateChangeLunchB() {
            itemViewHolder.binding.todayLunchBHateTv.setTextColor(Color.parseColor("#274984"))
            itemViewHolder.binding.todayLunchBGoodTv.setTextColor(Color.parseColor("#CECECE"))
            itemViewHolder.binding.todayLunchBGoodIv.setColorFilter(Color.parseColor("#CECECE"))
            itemViewHolder.binding.todayLunchBHateIv.setColorFilter(Color.parseColor("#274984"))
        }

        fun getHateChangeDinner() {
            itemViewHolder.binding.todayAfternoonHateTv.setTextColor(Color.parseColor("#274984"))
            itemViewHolder.binding.todayAfternoonGoodTv.setTextColor(Color.parseColor("#CECECE"))
            itemViewHolder.binding.todayAfternoonGoodIv.setColorFilter(Color.parseColor("#CECECE"))
            itemViewHolder.binding.todayAfternoonHateIv.setColorFilter(Color.parseColor("#274984"))
        }

        fun defaultChangeA() {
            itemViewHolder.binding.todayLunchAHateTv.setTextColor(Color.parseColor("#717171"))
            itemViewHolder.binding.todayLunchAGoodTv.setTextColor(Color.parseColor("#717171"))
            itemViewHolder.binding.todayLunchAGoodIv.setColorFilter(Color.parseColor("#717171"))
            itemViewHolder.binding.todayLunchAHateIv.setColorFilter(Color.parseColor("#717171"))
        }

        fun defaultChangeB() {
            itemViewHolder.binding.todayLunchBHateTv.setTextColor(Color.parseColor("#717171"))
            itemViewHolder.binding.todayLunchBGoodTv.setTextColor(Color.parseColor("#717171"))
            itemViewHolder.binding.todayLunchBGoodIv.setColorFilter(Color.parseColor("#717171"))
            itemViewHolder.binding.todayLunchBHateIv.setColorFilter(Color.parseColor("#717171"))
        }


        fun defaultChange() {
            itemViewHolder.binding.todayAfternoonHateTv.setTextColor(Color.parseColor("#717171"))
            itemViewHolder.binding.todayAfternoonGoodTv.setTextColor(Color.parseColor("#717171"))
            itemViewHolder.binding.todayAfternoonGoodIv.setColorFilter(Color.parseColor("#717171"))
            itemViewHolder.binding.todayAfternoonHateIv.setColorFilter(Color.parseColor("#717171"))
        }

        // 버튼 색 판단
        if (isItemSelected) {
            if (itemList[0].size.toString() == "3" || MyongsikApplication.prefs.getUserArea() == "S"){
                if (LUNCH_A_GOOD == "good") getGoodChangeLunchA()
                if (LUNCH_A_GOOD == "hate") getHateChangeLunchA()
                if (LUNCH_B_GOOD == "good") getGoodChangeLunchB()
                if (LUNCH_B_GOOD == "hate") getHateChangeLunchB()
                if (DINNER == "good") getGoodChangeDinner()
                if (DINNER == "hate") getHateChangeDinner()
            }
            if (MyongsikApplication.prefs.getUserArea() == "L"){
                if (LUNCH_A_GOOD_S == "good") getGoodChangeLunchA()
                if (LUNCH_A_GOOD_S == "hate") getHateChangeLunchA()
                if (DINNER_S == "good") getGoodChangeDinner()
                if (DINNER_S == "hate") getHateChangeDinner()
                }
            if (MyongsikApplication.prefs.getUserArea() == "H"){
                if (LUNCH_A_GOOD_H == "good") getGoodChangeLunchA()
                if (LUNCH_A_GOOD_H == "hate") getHateChangeLunchA()
                if (DINNER_H == "good") getGoodChangeDinner()
                if (DINNER_H == "hate") getHateChangeDinner()
            }
        }

        if (isItemSelected) {
            itemViewHolder.binding.todayGoodClLunchB.setOnClickListener {
                // lunch B - 자캠 불가능
                val dialogUtils = DialogUtils(holder.itemView.context)
                if (LUNCH_B_GOOD == "good") {
                    if (itemViewHolder.binding.todayLunchBGoodTv.currentTextColor == Color.parseColor(
                            "#274984"
                        )
                    ) {
                        defaultChangeB()
                        homeViewModel.saveLunchEvaluation("B", "")
                        return@setOnClickListener
                    }
                }

                dialogUtils.showAlertDialog(
                    "‘맛있어요’로\n" +
                            "학식 평가를 하시겠어요?",
                    6,
                    yesClickListener = {
                        // api

                        // local
                        // 인캠의 경우
                        LUNCH_B_GOOD = "good"
                        getGoodChangeLunchB()
                        homeViewModel.saveLunchEvaluation("B", "good")

                    },
                    noClickListener = {

                    }
                )
            }

            itemViewHolder.binding.todayHateClLunchB.setOnClickListener {
                // lunch B - 자캠 불가능
                val dialogUtils = DialogUtils(holder.itemView.context)
                if (LUNCH_B_GOOD == "hate") {
                    if (itemViewHolder.binding.todayLunchBHateTv.currentTextColor == Color.parseColor(
                            "#274984"
                        )
                    ) {
                        defaultChangeB()
                        homeViewModel.saveLunchEvaluation("B", "")
                        return@setOnClickListener
                    }
                }

                dialogUtils.showAlertDialog(
                    "‘맛없어요’로\n" +
                            "학식 평가를 하시겠어요?",
                    6,
                    yesClickListener = {
                        // api

                        // local
                        // 인캠의 경우
                        LUNCH_B_GOOD = "hate"
                        getHateChangeLunchB()
                        homeViewModel.saveLunchEvaluation("B", "hate")

                    },
                    noClickListener = {

                    }
                )
            }

            itemViewHolder.binding.todayGoodCl.setOnClickListener {
                val dialogUtils = DialogUtils(holder.itemView.context)
                if (itemList[0].size.toString() == "3" || MyongsikApplication.prefs.getUserArea() == "S") {
                    if (LUNCH_A_GOOD == "good") {
                        if (itemViewHolder.binding.todayLunchAGoodTv.currentTextColor == Color.parseColor(
                                "#274984"
                            )
                        ) {
                            defaultChangeA()
                            homeViewModel.saveLunchEvaluation("A", "")
                            return@setOnClickListener
                        }
                    }
                } else {
                        if (MyongsikApplication.prefs.getUserArea() == "L") {
                            if (LUNCH_A_GOOD_S == "good") {
                                if (itemViewHolder.binding.todayLunchAGoodTv.currentTextColor == Color.parseColor(
                                    "#274984"
                                )
                            ) {
                                defaultChangeA()
                                homeViewModel.saveLunchEvaluation("AS", "")
                                return@setOnClickListener
                            }
                        }
                    }
                    if (MyongsikApplication.prefs.getUserArea() == "H") {
                        if (LUNCH_A_GOOD_H == "good") {
                            if (itemViewHolder.binding.todayLunchAGoodTv.currentTextColor == Color.parseColor(
                                    "#274984"
                                )
                            ) {
                                defaultChangeA()
                                homeViewModel.saveLunchEvaluation("AH", "")
                                return@setOnClickListener
                            }
                        }
                    }
                }

                dialogUtils.showAlertDialog(
                    "‘맛있어요’로\n" +
                            "학식 평가를 하시겠어요?",
                    6,
                    yesClickListener = {
                        // api

                        // local
                        // 인캠의 경우
                        if (itemList[0].size.toString() == "3" || MyongsikApplication.prefs.getUserArea() == "S") {
                            LUNCH_A_GOOD = "good"
                            getGoodChangeLunchA()
                            homeViewModel.saveLunchEvaluation("A", "good")
                        }
                        // 자캠의 경우
                        else {
                            if (MyongsikApplication.prefs.getUserArea() == "L") {
                                LUNCH_A_GOOD_S = "good"
                                getGoodChangeLunchA()
                                homeViewModel.saveLunchEvaluation("AS", "good")

                            }
                            if (MyongsikApplication.prefs.getUserArea() == "H") {
                                LUNCH_A_GOOD_H = "good"
                                getGoodChangeLunchA()
                                homeViewModel.saveLunchEvaluation("AH", "good")

                            }
                        }
                    },
                    noClickListener = {

                    })
            }

            itemViewHolder.binding.todayHateClLunchA.setOnClickListener {
                val dialogUtils = DialogUtils(holder.itemView.context)
                if (itemList[0].size.toString() == "3" || MyongsikApplication.prefs.getUserArea() == "S") {
                    if (LUNCH_A_GOOD == "hate") {
                        if (itemViewHolder.binding.todayLunchAHateTv.currentTextColor == Color.parseColor(
                                "#274984"
                            )
                        ) {

                            defaultChangeA()
                            homeViewModel.saveLunchEvaluation("A", "")
                            return@setOnClickListener
                        }
                    }
                }
                if (MyongsikApplication.prefs.getUserArea() == "L") {
                    if (LUNCH_A_GOOD_S == "hate") {
                        if (itemViewHolder.binding.todayLunchAHateTv.currentTextColor == Color.parseColor(
                                "#274984"
                            )
                        ) {

                            defaultChangeA()
                            homeViewModel.saveLunchEvaluation("AS", "")
                            return@setOnClickListener
                        }
                    }
                }
                if (MyongsikApplication.prefs.getUserArea() == "H") {
                    if (LUNCH_A_GOOD_H == "hate") {
                        if (itemViewHolder.binding.todayLunchAHateTv.currentTextColor == Color.parseColor(
                                "#274984"
                            )
                        ) {
                            defaultChangeA()
                            homeViewModel.saveLunchEvaluation("AH", "")
                            return@setOnClickListener
                        }
                    }
                }

                dialogUtils.showAlertDialog(
                    "‘맛없어요’로\n" +
                            "학식 평가를 하시겠어요?",
                    6,
                    yesClickListener = {
                        // 인캠의 경우
                        if (itemList[0].size.toString() == "3" || MyongsikApplication.prefs.getUserArea() == "S") {
                            LUNCH_A_GOOD = "hate"
                            getHateChangeLunchA()
                            homeViewModel.saveLunchEvaluation("A", "hate")
                        }
                        // 자캠의 경우
                        else {
                            if (MyongsikApplication.prefs.getUserArea() == "L") {
                                LUNCH_A_GOOD_S = "hate"
                                getHateChangeLunchA()
                                homeViewModel.saveLunchEvaluation("AS", "hate")
                            }
                            if (MyongsikApplication.prefs.getUserArea() == "H") {
                                LUNCH_A_GOOD_H = "hate"
                                getHateChangeLunchA()
                                homeViewModel.saveLunchEvaluation("AH", "hate")
                            }
                        }
                    },
                    noClickListener = {

                    }
                )
            }

            itemViewHolder.binding.todayGoodClAfternoon.setOnClickListener {
                val dialogUtils = DialogUtils(holder.itemView.context)
                if (itemList[0].size.toString() == "3" || MyongsikApplication.prefs.getUserArea() == "S") {
                    if (DINNER == "good") {
                        if (itemViewHolder.binding.todayAfternoonGoodTv.currentTextColor == Color.parseColor(
                                "#274984"
                            )
                        ) {

                            defaultChange()
                            homeViewModel.saveLunchEvaluation("D", "")
                            return@setOnClickListener
                        }
                    }
                }
                    if (MyongsikApplication.prefs.getUserArea() == "L") {
                        if (DINNER_S == "good") {
                            if (itemViewHolder.binding.todayAfternoonGoodTv.currentTextColor == Color.parseColor(
                                "#274984"
                            )
                        ) {

                            defaultChange()
                            homeViewModel.saveLunchEvaluation("DS", "")
                            return@setOnClickListener
                        }
                    }
                }
                if (MyongsikApplication.prefs.getUserArea() == "H") {
                    if (DINNER_H == "good") {
                        if (itemViewHolder.binding.todayAfternoonGoodTv.currentTextColor == Color.parseColor(
                                "#274984"
                            )
                        ) {

                            defaultChange()
                            homeViewModel.saveLunchEvaluation("DH", "")
                            return@setOnClickListener
                        }
                    }
                }

                dialogUtils.showAlertDialog(
                    "‘맛있어요’로\n" +
                            "학식 평가를 하시겠어요?",
                    6,
                    yesClickListener = {
                        // api

                        // local
                        // 인캠의 경우
                        if (itemList[0].size.toString() == "3" || MyongsikApplication.prefs.getUserArea() == "S") {
                            DINNER = "good"
                            getGoodChangeDinner()
                            homeViewModel.saveLunchEvaluation("D", "good")
                        }
                        // 자캠의 경우
                        else {
                            if (MyongsikApplication.prefs.getUserArea() == "S") {

                                DINNER_S = "good"
                                getGoodChangeDinner()

                                homeViewModel.saveLunchEvaluation("DS", "good")
                            }
                            if (MyongsikApplication.prefs.getUserArea() == "H") {

                                DINNER_H = "good"
                                getGoodChangeDinner()

                                homeViewModel.saveLunchEvaluation("DH", "good")
                            }
                        }
                    },
                    noClickListener = {

                    }
                )
            }


            itemViewHolder.binding.todayHateCl.setOnClickListener {
                if (itemList[0].size.toString() == "3" || MyongsikApplication.prefs.getUserArea() == "S") {
                    if (DINNER == "hate") {
                        if (itemViewHolder.binding.todayAfternoonHateTv.currentTextColor == Color.parseColor(
                                "#274984"
                            )
                        ) {
                            defaultChange()
                            homeViewModel.saveLunchEvaluation("D", "")
                            return@setOnClickListener
                        }
                    }
                }
                    if (MyongsikApplication.prefs.getUserArea() == "L") {
                        if (DINNER_S == "hate") {
                            if (itemViewHolder.binding.todayAfternoonHateTv.currentTextColor == Color.parseColor(
                                "#274984"
                            )
                        ) {
                            defaultChange()
                            homeViewModel.saveLunchEvaluation("DS", "")
                            return@setOnClickListener
                        }
                    }
                }
                if (MyongsikApplication.prefs.getUserArea() == "H") {
                    if (DINNER_H == "hate") {
                        if (itemViewHolder.binding.todayAfternoonHateTv.currentTextColor == Color.parseColor(
                                "#274984"
                            )
                        ) {

                            defaultChange()
                            homeViewModel.saveLunchEvaluation("DH", "")
                            return@setOnClickListener

                        }
                    }
                }

                val dialogUtils = DialogUtils(holder.itemView.context)
                dialogUtils.showAlertDialog(
                    "‘맛없어요’로\n" +
                            "학식 평가를 하시겠어요?",
                    6,
                    yesClickListener = {
                        // 인캠의 경우 , 자캠 교직원
                        if (itemList[0].size.toString() == "3" || MyongsikApplication.prefs.getUserArea() == "S") {
                            DINNER = "hate"
                            getHateChangeDinner()

                            homeViewModel.saveLunchEvaluation("D", "hate")
                        }
                        // 자캠 생활관
                        else {
                            if (MyongsikApplication.prefs.getUserArea() == "L") {
                                DINNER_S = "hate"
                                getHateChangeDinner()
                                homeViewModel.saveLunchEvaluation("DS", "hate")
                            }
                            if (MyongsikApplication.prefs.getUserArea() == "H") {

                                DINNER_H = "hate"
                                getHateChangeDinner()

                                homeViewModel.saveLunchEvaluation("DH", "hate")
                            }
                        }
                    },
                    noClickListener = {

                    }
                )
            }
        } else {
            holder.itemView.setOnClickListener(null)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}
