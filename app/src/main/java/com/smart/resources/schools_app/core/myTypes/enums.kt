package com.smart.resources.schools_app.core.myTypes

enum class Section{
    HOMEWORK,
    LIBRARY,
    SCHEDULE,
    ABSENCE,
    RATING,
    ADVERTISING,
    EXAM,
    NOTIFICATION,
    LECTURE,
    ONLINE_EXAM,
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