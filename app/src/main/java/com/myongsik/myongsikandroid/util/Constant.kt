package com.myongsik.myongsikandroid.util

import com.myongsik.myongsikandroid.BuildConfig

object Constant {
    //Retrofit Base Url
    const val MYONG_SIK_BASE_URL = "http://13.209.50.30"
    const val KAKAO_BASE_URL = "https://dapi.kakao.com"

    const val S = "S"
    const val Y = "Y"
    const val SEOUL = "SEOUL"
    const val YONGIN = "YONGIN"

    const val SEOUL_CAMPUS = "서울 명지대"
    const val YONGIN_CAMPUS = "용인 명지대"
    const val CATEGORY_GROUP_CODE = "FD6, CE7"

    const val SEOUL_CAMPUS_X = 126.923460283882
    const val SEOUL_CAMPUS_Y = 37.5803504797164

    const val YONGIN_CAMPUS_X = 127.18758354347
    const val YONGIN_CAMPUS_Y = 37.224650469991

    const val DISTANCE = "distance"
    const val SCRAP_COUNT = "scrapCount"
    const val DESC = "desc"
    const val ASC = "asc"

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

    //Admob
    var isAdAvailable = true
}