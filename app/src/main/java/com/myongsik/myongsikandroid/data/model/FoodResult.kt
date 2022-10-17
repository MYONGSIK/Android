package com.myongsik.myongsikandroid.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity

//오늘 식단 조회
//맛있어요 맛없어요를 로컬디비에 저장하기 위해 설정
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
    //임의로 넣어논 음식평가 변수 (서버에서 받는 변수 아님)
    val condition : Boolean
)
