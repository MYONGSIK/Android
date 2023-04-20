package com.myongsik.myongsikandroid.data.type

enum class WidgetType(type:String) {

    DORMITORY("dormitory"), MYONGJIN("myongjin"), STUDENT("student"), TEACHER("teacher")
}

fun String.toWidgetType() : WidgetType {
    return when(this) {
        WidgetType.DORMITORY.name -> WidgetType.DORMITORY
        WidgetType.MYONGJIN.name -> WidgetType.MYONGJIN
        WidgetType.STUDENT.name -> WidgetType.STUDENT
        WidgetType.TEACHER.name -> WidgetType.TEACHER
        else -> WidgetType.DORMITORY
    }
}