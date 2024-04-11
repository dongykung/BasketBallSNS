package com.dkproject.presentation.util


import android.util.Log
import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter
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
    val formatter = SimpleDateFormat("a hh:mm", Locale.getDefault())
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

fun displayDateInfo(milliseconds: Long): String {
    // 현재 시간대를 기준으로 Instant 생성
    // Api level 호환하기 위해 ThreetenABP 의 Instant 사용
    val instant = Instant.ofEpochMilli(milliseconds)
    val dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
    val now = LocalDateTime.now(ZoneId.systemDefault())
    Log.d("now-time : ", now.toString())
    Log.d("now-time : ", now.toLocalDate().toString())

    return if(dateTime.toLocalDate().isEqual(now.toLocalDate())){
        dateTime.format(DateTimeFormatter.ofPattern("a hh:mm"))
    }else if(dateTime.toLocalDate().isEqual(now.toLocalDate().minusDays(1))){
        "어제"
    }else{
        dateTime.format(DateTimeFormatter.ofPattern("MM-dd"))
    }
}
