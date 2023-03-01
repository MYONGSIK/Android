package com.myongsik.myongsikandroid.data.model.food

//주간 식단 조회
data class WeekFoodResult(
    val mealId: Int,
    val toDay: String,
    val mealType : String,
    val statusType : String,
    val meals : List<String>
)