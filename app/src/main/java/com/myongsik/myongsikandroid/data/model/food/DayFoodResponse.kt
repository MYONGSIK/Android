package com.myongsik.myongsikandroid.data.model.food

data class DayFoodResponse(
    val `data`: List<Data>,
    val httpCode: Int,
    val httpStatus: String,
    val localDateTime: String,
    val message: String,
    val success: Boolean
) {
    data class Data(
        val mealId: Int,
        val mealType: String,
        val meals: List<String>,
        val statusType: String,
        val toDay: String
    )
}