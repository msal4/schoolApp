package com.smart.resources.schools_app.core.util

enum class Section{
    HOMEWORK,
    EXAM,
    LIBRARY,
    SCHEDULE,
    ABSENCE,
}


enum class WeekDays(val dayName:String) {

    SATURDAY("السبت"),
    SUNDAY("الاحد"),
    MONDAY("الاثنين"),
    TUESDAY( "الثلاثاء"),
    WEDNESDAY("الاربعاء"),
    THURSDAY( "الخميس"),
    FRIDAY("الجمعة");

    companion object{
        fun getDayName(pos: Int)= values()[pos].dayName
    }
}

enum class UserType{
    STUDENT,
    TEACHER

}

enum class NotificationType{
    STUDENT,
    SECTION
}