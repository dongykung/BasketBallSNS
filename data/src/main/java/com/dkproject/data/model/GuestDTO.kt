package com.dkproject.data.model

import com.dkproject.domain.model.home.Guest

data class GuestDTO(
    val writeUid:String="",
    val uid:String="",
    val title:String="",
    val positionList:List<String> = emptyList(),
    val count:Int = 0,
    val content:String="",
    val lat:Double=0.0,
    val lng:Double=0.0,
    val detailAddress:String="",
    val guestsUid:List<String> = emptyList(),
    val detaildate:Long=System.currentTimeMillis(),
    val daydate:Long = System.currentTimeMillis(),
    val geohash:String ?=null,
){
    fun toDomainModel():Guest{
        return Guest(
            writeUid=writeUid,
            uid=uid,
            title=title,
            positionList=positionList,
            count=count,
            content=content,
            lat=lat,
            lng=lng,
            guestsUid=guestsUid,
            detailAddress=detailAddress,
            detaildate =detaildate,
            daydate=daydate
        )

    }
}