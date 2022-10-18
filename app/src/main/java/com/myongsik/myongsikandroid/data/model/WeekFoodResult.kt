package com.myongsik.myongsikandroid.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity

//주간 식단 조회
data class WeekFoodResult(
    val dayOfTheWeek: String,
    val toDay: String,
    val status : String,
    val food1 : String,
    val food2 : String,
    val food3 : String,
    val food4 : String,
)
/*
"toDay": "2022-10-18",
      "dayOfTheWeek": "화요일",
      "classification": "중식",
      "type": "A",
      "status": "운영",
      "food1": "부대찌개",
      "food2": "쌀밥",
      "food3": "치킨가라아게&갈릭마요소스",
      "food4": "도시락김",
      "food5": "무말랭이무침",
      "food6": "배추김치"
 */