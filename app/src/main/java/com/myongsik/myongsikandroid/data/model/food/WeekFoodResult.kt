package com.myongsik.myongsikandroid.data.model.food

data class WeekFoodResult(
    val mealId: Int,
    val toDay: String,
    val mealType : String,
    val statusType : String,
    val meals : List<String>
)