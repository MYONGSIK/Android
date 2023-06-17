package com.myongsik.data.type

enum class WidgetType(val type:String) {
    DORMITORY("생활관식당"), MYONGJIN("명진당식당"), STUDENT("학생식당"), TEACHER("교직원식당")
}

fun String.toWidgetType() : WidgetType {
    return when(this) {
        WidgetType.DORMITORY.type -> WidgetType.DORMITORY
        WidgetType.MYONGJIN.type -> WidgetType.MYONGJIN
        WidgetType.STUDENT.type -> WidgetType.STUDENT
        WidgetType.TEACHER.type -> WidgetType.TEACHER
        else -> WidgetType.DORMITORY
    }
}