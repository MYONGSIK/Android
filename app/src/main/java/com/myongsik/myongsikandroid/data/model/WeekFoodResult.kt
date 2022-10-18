package com.myongsik.myongsikandroid.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity

//주간 식단 조회
data class WeekFoodResult(
    val dayOfTheWeek: String,
    val toDay: String,
    val status : String,
    val lunchA : List<String>,
    val lunchB : List<String>,
    val dinner : List<String>
)
/*
 "toDay": "2022-10-17",
      "status": "운영",
      "dayOfTheWeek": "월요일",
      "lunchA": [
        "베이컨김치볶음밥&후라이",
        "맑은우동국물",
        "피쉬앤칩스&케찹",
        "단무지",
        "배추김치",
        " "
      ],
      "lunchB": [
        "모짜렐라치즈돈가츠",
        "맑은우동국물",
        "추가밥",
        "스위트콘&그린샐러드",
        "오이피클",
        "배추김치"
      ],
      "dinner": [
        "해물볶음우동",
        "맑은우동국물",
        "추가밥",
        "스위트콘&그린샐러드",
        "단무지",
        "배추김치"
      ]
 */