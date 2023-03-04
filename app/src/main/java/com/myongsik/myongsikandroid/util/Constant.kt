package com.myongsik.myongsikandroid.util

import com.myongsik.myongsikandroid.BuildConfig

object Constant {
    //Retrofit Base Url
    const val MYONG_SIK_BASE_URL = "http://13.209.50.30"

    //DataStore Name
    const val DATASTORE_NAME = "food_evaluation_datastore"

    //kakao key
    const val API_KEY = BuildConfig.kakaoApiKey

    const val SEARCH_FOODS_TIME_DELAY = 50L

    //Good, Hate
    //중식 A 평가
    var LUNCH_A_GOOD = ""
    var LUNCH_A_GOOD_S = ""
    var LUNCH_A_GOOD_H = ""

    //중식 A 평가
    var LUNCH_B_GOOD = ""

    //석식 평가
    var DINNER = ""

    var DINNER_S = ""
    var DINNER_H = ""


    //Paging
    const val PAGING_SIZE = 15
}