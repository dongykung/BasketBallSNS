package com.dkproject.presentation.util

import com.dkproject.domain.model.home.Guest
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


fun converTimeMills(dateMIlls:Long,hour:Int,minute:Int):Long{
    val cal = Calendar.getInstance().apply {
        timeInMillis = dateMIlls
        set(Calendar.HOUR_OF_DAY,hour)
        set(Calendar.MINUTE,minute)
        set(Calendar.SECOND,0)
        set(Calendar.MILLISECOND,0)
    }
    return cal.timeInMillis
}

fun converMillisToMonthday(millis:Long):String{
    val formatter = SimpleDateFormat("MM.dd", Locale.getDefault())
    return formatter.format(millis)
}

fun convertiChatTimeMillis(millis:Long):String{
    val formatter = SimpleDateFormat("hh:mm a")
    return formatter.format(millis)
}

fun getDayOfWeekFromMillis(millis:Long):String{
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = millis
    return when(calendar.get(Calendar.DAY_OF_WEEK)){
        Calendar.SUNDAY ->"일"
        Calendar.MONDAY->"월"
        Calendar.TUESDAY->"화"
        Calendar.WEDNESDAY->"수"
        Calendar.THURSDAY->"목"
        Calendar.FRIDAY->"금"
        Calendar.SATURDAY->"토"
        else -> ""
    }
}

