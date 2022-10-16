package com.myongsik.myongsikandroid.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName="food", primaryKeys = arrayOf("classification", "today"))
data class FoodResult(
    val classification: String,
    val dayOfTheWeek: String,
    val food1: String,
    val food2: String,
    val food3: String,
    val food4: String,
    val food5: String,
    val food6: String,
    val status: String,
    @ColumnInfo(name = "today")
    val toDay: String,
    val condition : Boolean
)
