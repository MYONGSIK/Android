package com.myongsik.myongsikandroid.data.model.food

data class FoodResult(
    val classification: String,
    val dayOfTheWeek: String,
    val type : String?,
    val food1: String,
    val food2: String,
    val food3: String,
    val food4: String,
    val food5: String,
    val food6: String,
    val status: String,
    val toDay: String,
    val condition : Boolean
)
