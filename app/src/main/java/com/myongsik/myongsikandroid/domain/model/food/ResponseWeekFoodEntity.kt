package com.myongsik.myongsikandroid.domain.model.food

data class ResponseWeekFoodEntity(
    val success : Boolean,
    val message : String,
    val localDateTime : String,
    val httpStatus : String,
    val httpCode : Int,
    val data : List<WeekFoodResultEntity>,
){
    data class WeekFoodResultEntity(
        val mealId: Int,
        val toDay: String,
        val mealType : String,
        val statusType : String,
        val meals : List<String>
    )
}
