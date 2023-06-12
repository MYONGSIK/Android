package com.myongsik.presentation.util

import java.text.SimpleDateFormat
import java.util.*

object DateUtil {
    private val dateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.KOREA)


    fun getCurrentDate(): String {
        return dateFormat.format(Date())
    }

    fun getDelayUntilNextMidnight(): Long {
        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 5)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
            if (timeInMillis < System.currentTimeMillis()) {
                add(Calendar.DAY_OF_MONTH, 1)
            }
        }
        return calendar.timeInMillis - System.currentTimeMillis()
    }
}